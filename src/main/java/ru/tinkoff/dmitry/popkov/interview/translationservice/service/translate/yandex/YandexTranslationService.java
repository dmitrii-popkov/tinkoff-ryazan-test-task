package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.yandex;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiLanguagesRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TextTokensProcessor;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TranslationService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.WordTranslator;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod")
public class YandexTranslationService implements TranslationService {
    // TODO: 3/16/22 REST errors handling

    private final YandexTranslator translator;
    private final WordTranslator wordTranslator;
    private final TextTokensProcessor textTokensProcessor;

    @Setter(onMethod = @__(@Value("${services.translate.yandex.folder}")))
    private String cloudFolderId;

    @Override
    public TranslationResult translate(TranslationRequest translationRequest) {
        String targetLanguage = translationRequest.getTarget();
        String translatedText = textTokensProcessor.mapWords(translationRequest.getText(),
                word -> wordTranslator.translate(word, targetLanguage));
        return TranslationResult.builder()
                .translatedText(translatedText).build();
    }

    @Override
    public LanguageList getAcceptedLanguages() {
        return translator.getAvailableLanguages(new YandexApiLanguagesRequest(cloudFolderId));
    }
}