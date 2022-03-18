package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate;

import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationRequest;

import java.util.List;

public interface TranslationService {
    TranslationResultDto translate(TranslationRequest translationRequest);
    LanguageList getAcceptedLanguages();
    default String mergeWords(List<TranslationDto> words) {
        StringBuilder outputText = new StringBuilder();
        for (TranslationDto word : words) {
            outputText.append(word.getDelimeterBefore())
                    .append(word.getTargetWord())
                    .append(word.getDelimeterAfter());
        }
        return outputText.toString();
    }

}
