package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TranslationServiceImpl implements TranslationService {

    // TODO: 3/16/22 REST errors handling

    private final WebClient yandexClient;

    @Setter(onMethod = @__(@Value("${services.translate.yandex.folder}")))
    private String cloudFolderId;
    
    @Override
    public TranslationResult translate(TranslationRequest translationRequest) {
        YandexApiTranslationRequest request = YandexApiTranslationRequest.builder()
                .folderId(cloudFolderId)
                .targetLanguageCode(translationRequest.getTarget())
                .texts(List.of(translationRequest.getText())).build();
        ResponseEntity<YandexApiTranslationResponse> res = yandexClient.post()
                .uri("/translate")
                .bodyValue(request)
                .retrieve()
                .toEntity(YandexApiTranslationResponse.class)
                .block();
        YandexApiTranslationResponse yandexResponse = res.getBody();
        TranslationResult translationResult = TranslationResult.builder()
                .translatedText(yandexResponse.getTranslations().get(0).getText())
                .build();
        return translationResult;
    }

    @Override
    public LanguageList getAcceptedLanguages() {
        return yandexClient.post()
                .uri("/languages")
                .bodyValue(new YandexApiLanguagesRequest(cloudFolderId))
                .retrieve()
                .toEntity(LanguageList.class)
                .block().getBody();
    }
}
