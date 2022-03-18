package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.duckduckgo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.in.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.TranslationResultDto;
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
    public TranslationResultDto translate(TranslationRequest translationRequest) {
        String targetLanguage = translationRequest.getTarget();
        TranslationResultDto translatedResultDto = textTokensProcessor.mapWords(translationRequest.getText(),
                word -> wordTranslator.translate(word, targetLanguage));
        translatedResultDto.setInputText(translationRequest.getText());
        translatedResultDto.setLanguageCode(targetLanguage);
        translatedResultDto.setOutputText(mergeWords(translatedResultDto.getTranslations()));
        return translatedResultDto;
    }

    @Override
    public LanguageList getAcceptedLanguages() {
        return translator.getAvailableLanguages();
    }
}
