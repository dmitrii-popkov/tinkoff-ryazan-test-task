package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class YandexApiTranslationRequest {
    private String folderId;
    private List<String> texts;
    private String targetLanguageCode;
}
