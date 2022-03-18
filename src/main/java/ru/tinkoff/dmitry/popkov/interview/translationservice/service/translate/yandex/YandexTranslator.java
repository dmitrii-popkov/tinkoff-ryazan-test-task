package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.yandex;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.ApiToken;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiLanguagesRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationResponse;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod")
public class YandexTranslator {

    private static final String TRANSLATE_URI = "/translate";
    private static final String GET_LANGUEAGES_URI = "/languages";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final WebClient yandexClient;
    @Setter(onMethod = @__(@Autowired))
    private ApiToken iamToken;

    public YandexApiTranslationResponse translate(YandexApiTranslationRequest yandexApiTranslationRequest) {
        return yandexClient.post()
                .uri(TRANSLATE_URI)
                .header(AUTHORIZATION_HEADER, iamToken.getToken())
                .bodyValue(yandexApiTranslationRequest)
                .retrieve()
                .toEntity(YandexApiTranslationResponse.class)
                .block().getBody();
    }
    public LanguageList getAvailableLanguages(YandexApiLanguagesRequest yandexApiLanguagesRequest) {
        return yandexClient.post()
                .uri(GET_LANGUEAGES_URI)
                .header(AUTHORIZATION_HEADER, iamToken.getToken())
                .bodyValue(yandexApiLanguagesRequest)
                .retrieve()
                .toEntity(LanguageList.class)
                .block().getBody();
    }

}
