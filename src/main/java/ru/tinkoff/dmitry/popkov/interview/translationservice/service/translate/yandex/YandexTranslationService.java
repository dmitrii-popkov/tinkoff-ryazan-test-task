package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.yandex;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.in.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.TranslationResultDto;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiLanguagesRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TextTokensProcessor;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TranslationService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.WordTranslator;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod")
public class YandexTranslationService implements TranslationService {

    private final YandexTranslator translator;
    private final WordTranslator wordTranslator;
    private final TextTokensProcessor textTokensProcessor;

    @Setter(onMethod = @__(@Value("${services.translate.yandex.folder}")))
    private String cloudFolderId;

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
        return translator.getAvailableLanguages(new YandexApiLanguagesRequest(cloudFolderId));
    }
}
