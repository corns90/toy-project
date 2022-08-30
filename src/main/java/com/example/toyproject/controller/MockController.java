package com.example.toyproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toyproject.model.MockResponse;

@RestController
@RequestMapping("/api")
public class MockController {

    @GetMapping("/success")
    public ResponseEntity<MockResponse> success() {
        return new ResponseEntity<>(MockResponse.builder().uuid("success").build(), HttpStatus.OK);
    }

    @GetMapping("/fail")
    public ResponseEntity<MockResponse> fail() {
        MockResponse.Detail detail = new MockResponse.Detail();
        return new ResponseEntity<>(MockResponse.builder().uuid("fail").detail(detail).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/fail2")
    public ResponseEntity<MockResponse> fail2() throws InterruptedException {
        MockResponse.Detail detail = new MockResponse.Detail();
        Thread.sleep(10000);
        return new ResponseEntity<>(MockResponse.builder().uuid("fail2").detail(detail).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
