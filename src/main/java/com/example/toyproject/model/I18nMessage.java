package com.example.toyproject.model;

import java.time.LocalDateTime;
import java.util.Locale;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class I18nMessage {
    private String messageKey;
    private String i18nCategoryType;
    private Locale locale;
    private String message;
    private LocalDateTime registrationDate;
    private LocalDateTime recentModifiedDate;
}
