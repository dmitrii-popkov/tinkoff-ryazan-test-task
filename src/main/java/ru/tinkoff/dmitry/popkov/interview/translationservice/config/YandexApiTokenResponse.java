package ru.tinkoff.dmitry.popkov.interview.translationservice.config;

import lombok.Data;

@Data
public class YandexApiTokenResponse {
    private String iamToken;
    private String expiresAt;
}
