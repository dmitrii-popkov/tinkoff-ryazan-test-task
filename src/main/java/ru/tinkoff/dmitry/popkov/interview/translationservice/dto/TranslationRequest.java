package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Data;

@Data
public class TranslationRequest {
    private String targetLanguage;
    private String text;
}
