package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate;

import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationRequest;

import java.util.List;

public interface TranslationService {
    TranslationResult translate(TranslationRequest translationRequest);
    LanguageList getAcceptedLanguages();
}
