package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.yandex;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.ApiToken;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiLanguagesRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationResponse;

import javax.servlet.ServletContext;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod")
public class YandexTranslator {

    private final WebClient yandexClient;
    @Setter(onMethod = @__(@Autowired))
    private ApiToken iamToken;

    public YandexApiTranslationResponse translate(YandexApiTranslationRequest yandexApiTranslationRequest) {
        return yandexClient.post()
                .uri("/translate")
                .header("Authorization", iamToken.getToken())
                .bodyValue(yandexApiTranslationRequest)
                .retrieve()
                .toEntity(YandexApiTranslationResponse.class)
                .block().getBody();
    }
    public LanguageList getAvailableLanguages(YandexApiLanguagesRequest yandexApiLanguagesRequest) {
        return yandexClient.post()
                .uri("/languages")
                .header("Authorization", iamToken.getToken())
                .bodyValue(yandexApiLanguagesRequest)
                .retrieve()
                .toEntity(LanguageList.class)
                .block().getBody();
    }

}
