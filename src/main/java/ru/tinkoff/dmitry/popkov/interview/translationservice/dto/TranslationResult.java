package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationResult {
    private Language targetLanguage;
    private String translatedText;
}
