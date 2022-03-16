package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;

public interface TranslationService {
    TranslationResult translate(TranslationRequest translationRequest);

    LanguageList getAcceptedLanguages();
}
