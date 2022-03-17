package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiLanguagesRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationResponse;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod")
public class YandexTranslator {

    private final WebClient yandexClient;

    public YandexApiTranslationResponse translate(YandexApiTranslationRequest yandexApiTranslationRequest) {
        return yandexClient.post()
                .uri("/translate")
                .bodyValue(yandexApiTranslationRequest)
                .retrieve()
                .toEntity(YandexApiTranslationResponse.class)
                .block().getBody();
    }
    public LanguageList getAvailableLanguages(YandexApiLanguagesRequest yandexApiLanguagesRequest) {
        return yandexClient.post()
                .uri("/languages")
                .bodyValue(yandexApiLanguagesRequest)
                .retrieve()
                .toEntity(LanguageList.class)
                .block().getBody();
    }
}
