package com.example.toyproject.repository;

import java.util.List;

import com.example.toyproject.model.I18nMessage;

public interface I18nMessageMapper {
    List<I18nMessage> selectI18nMessageList(String i18nCategoryType);
}
