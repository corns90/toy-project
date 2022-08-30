package com.example.toyproject.service;

import static java.util.stream.Collectors.*;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.toyproject.model.I18nMessage;
import com.example.toyproject.repository.I18nMessageMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class I18nMessageService {
    private static final JavaPropsMapper JAVA_PROPS_MAPPER = new JavaPropsMapper();
    private final I18nMessageMapper mapper;

    private LoadingCache<String, Map<Locale, Map<String, String>>> messageMapCache = CacheBuilder.newBuilder()
        .expireAfterWrite(4L, TimeUnit.SECONDS)
        .build(new CacheLoader<String, Map<Locale, Map<String, String>>>() {
            @Override
            public Map<Locale, Map<String, String>> load(String i18nCategoryType) throws Exception {
                log.info("#### cache 를 로딩을 시작합니다. : {} ", i18nCategoryType);
                Thread.sleep(50000);
                log.info("#### cache 를 로딩이 끝납니다. : {} ", i18nCategoryType);
                return mapper.selectI18nMessageList(i18nCategoryType)
                    .stream()
                    .collect(groupingBy(I18nMessage::getLocale, toMap(I18nMessage::getMessageKey, I18nMessage::getMessage)));
            };
        });

    /**
     * 다국어 메시지 맵을 가져온다.
     * 로컬 캐시를 사용
     * @param i18nCategoryType
     * @return
     */
    public Mono<Map<Locale, Map<String, String>>> getMessagesMap(String i18nCategoryType) {
        return Mono.justOrEmpty(messageMapCache.getUnchecked(i18nCategoryType))
            .doOnError(e -> log.error(e.getMessage(), e));
    }

    /**
     * messagesMap을 JSON(ObjectNode)으로 만든다
     *
     * @param i18nCategoryType
     * @return
     */
    public Mono<ObjectNode> getMessagesJson(String i18nCategoryType) {
        return getMessagesMap(i18nCategoryType)
            .flatMapIterable(map -> map.entrySet())
            .reduce(JAVA_PROPS_MAPPER.createObjectNode(), this::accumulator)
            .doOnError(e -> log.error(e.getMessage(), e));
    }
    @SneakyThrows
    private ObjectNode accumulator(ObjectNode objectNode, Map.Entry<Locale, Map<String, String>> entry) {
        return objectNode.set(entry.getKey().toString(), JAVA_PROPS_MAPPER.readMapAs(entry.getValue(), ObjectNode.class));
    }
}
