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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YandexTranslationService implements TranslationService {
    // TODO: 3/16/22 REST errors handling

    private final YandexTranslator translator;
    private final TextTokensProcessor textTokensProcessor;

    @Setter(onMethod = @__(@Value("${services.translate.yandex.folder}")))
    private String cloudFolderId;

    @Override
    public TranslationResult translate(TranslationRequest translationRequest) {
        String targetLanguage = translationRequest.getTarget();
        String translatedText = textTokensProcessor.mapWords(translationRequest.getText(),
                word -> getTranslatedWord(word, targetLanguage));
        return TranslationResult.builder()
                .translatedText(translatedText).build();
    }

    private String getTranslatedWord(String word, String targetLanguageCode) {
        YandexApiTranslationRequest request = YandexApiTranslationRequest.builder()
                .folderId(cloudFolderId).targetLanguageCode(targetLanguageCode)
                .texts(List.of(word)).build();
        return translator.translate(request).getTranslations().get(0).getText();
    }
    @Override
    public LanguageList getAcceptedLanguages() {
        return translator.getAvailableLanguages(new YandexApiLanguagesRequest(cloudFolderId));
    }
}
