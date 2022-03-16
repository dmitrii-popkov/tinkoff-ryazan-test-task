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
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.YandexApiTranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.YandexApiTranslationResponse;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TranslationServiceImpl implements TranslationService {

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
}
