package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex;

import lombok.Data;

import java.util.List;

@Data
public class YandexApiTranslationResponse {
    private List<YandexApiTranslation> translations;
}
