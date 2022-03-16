package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class YandexApiTranslation {
    private String text;
    private String detectedLanguageCode;
}
