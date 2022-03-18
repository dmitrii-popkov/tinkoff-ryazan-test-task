package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.duckduckgo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TextTokensProcessor;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TranslationService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.WordTranslator;

@Service
@Profile("dev")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuckDuckGoTranslationService implements TranslationService {

    private final DuckDuckGoTranslator translator;
    private final WordTranslator wordTranslator;
    private final TextTokensProcessor textTokensProcessor;

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
        return translator.getAvailableLanguages();
    }
}
