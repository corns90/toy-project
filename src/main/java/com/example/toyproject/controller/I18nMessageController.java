package com.example.toyproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toyproject.service.I18nMessageService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/i18n")
@RequiredArgsConstructor
public class I18nMessageController {
    private final I18nMessageService i18nMessageService;

    @GetMapping("/categories/{i18nCategoryType}/messages")
    public Mono<ResponseEntity<ObjectNode>> getI18nMessageForForntEnd(@PathVariable String i18nCategoryType) {
        return i18nMessageService.getMessagesJson(i18nCategoryType)
            .map(json -> new ResponseEntity<>(json, HttpStatus.OK))
            .subscribeOn(Schedulers.elastic());
    }
}
