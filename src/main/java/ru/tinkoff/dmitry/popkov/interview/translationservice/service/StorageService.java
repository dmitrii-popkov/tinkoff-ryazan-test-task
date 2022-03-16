package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;

public interface StorageService {

    boolean saveTranslation(TranslationRequest request, TranslationResult result);
}
