package com.example.toyproject;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.toyproject.model.MockResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientMain {
    public static void main(String[] args) {
        WebClient webClient = WebClient.builder().build();

        MockResponse mockResponse = webClient.get()
            .uri("http://localhost:8080/api/success")
            .exchangeToMono(res -> res.bodyToMono(MockResponse.class))
            .block();

        log.info("###### result : {}, retry : {}", mockResponse, mockResponse.canRetry());

        mockResponse = webClient.get()
            .uri("http://localhost:8080/api/fail")
            .exchangeToMono(res -> res.bodyToMono(MockResponse.class))
            .block();

        log.info("###### result : {}, retry : {}", mockResponse, mockResponse.canRetry());

        mockResponse = webClient.get()
            .uri("http://localhost:8080/api/fail2")
            .exchangeToMono(res -> {
                return res.bodyToMono(MockResponse.class);
            })
            .block();


        log.info("###### result : {}, retry : {}", mockResponse, mockResponse.canRetry());
    }
}
