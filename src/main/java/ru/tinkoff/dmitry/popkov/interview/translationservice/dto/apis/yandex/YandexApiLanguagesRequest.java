package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YandexApiLanguagesRequest {
    private String folderId;
}
