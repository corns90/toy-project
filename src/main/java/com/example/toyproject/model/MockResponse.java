package com.example.toyproject.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 역직렬화시 필요
@AllArgsConstructor
@ToString
public class MockResponse {
    private String code;
    private String message;
    private String uuid;
    private Detail detail;

    public boolean canRetry() {
        return detail == null || detail.retryable;
    }

    @Getter
    @ToString
    public static class Detail {
        boolean retryable = false;
    }
}
