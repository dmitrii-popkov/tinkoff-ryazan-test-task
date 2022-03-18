package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex;

import lombok.Data;

@Data
public class YandexApiTranslation {
    private String text;
    private String detectedLanguageCode;
}
