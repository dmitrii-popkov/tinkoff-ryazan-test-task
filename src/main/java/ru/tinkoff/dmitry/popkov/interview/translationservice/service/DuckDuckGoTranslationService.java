package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;

import java.util.List;

@Service
@Profile("dev")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuckDuckGoTranslationService implements TranslationService {

    private final DuckDuckGoTranslator translator;
    private final TextTokensProcessor textTokensProcessor;

    @Override
    public TranslationResult translate(TranslationRequest translationRequest) {
        String targetLanguage = translationRequest.getTarget();
        String translatedText = textTokensProcessor.mapWords(translationRequest.getText(),
                word -> getTranslatedWord(word, targetLanguage));
        return TranslationResult.builder()
                .translatedText(translatedText).build();
    }

    private String getTranslatedWord(String word, String targetLanguageCode) {

        return translator.translate(word, targetLanguageCode).getTranslated();
    }
    @Override
    public LanguageList getAcceptedLanguages() {
        return translator.getAvailableLanguages();
    }
}
