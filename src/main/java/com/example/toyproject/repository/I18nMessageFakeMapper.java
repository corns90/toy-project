package com.example.toyproject.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.example.toyproject.model.I18nMessage;
import lombok.SneakyThrows;

@Component
public class I18nMessageFakeMapper implements I18nMessageMapper {
    @Override
    @SneakyThrows
    public List<I18nMessage> selectI18nMessageList(String i18nCategoryType) {
        List<I18nMessage> list = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            Locale locale = null;
            if (i % 5 == 0) {
                locale = Locale.ENGLISH;
            } else if (i % 5 == 1) {
                locale = Locale.KOREA;
            } else if (i % 5 == 2) {
                locale = Locale.JAPAN;
            } else if (i % 5 == 3) {
                locale = Locale.TRADITIONAL_CHINESE;
            } else if (i % 5 == 4) {
                locale = Locale.SIMPLIFIED_CHINESE;
            }
            list.add(I18nMessage.builder().messageKey("messageKey" + i).i18nCategoryType("WEB").locale(locale).message(
                "message").build());
        }
        return list;
    }
}
