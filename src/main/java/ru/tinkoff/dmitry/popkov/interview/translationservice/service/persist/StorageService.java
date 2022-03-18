package ru.tinkoff.dmitry.popkov.interview.translationservice.service.persist;

import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Language;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.TranslationRecord;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Word;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.WordTranslation;

import java.util.List;

public interface StorageService {

    void saveTranslation(TranslationRecord translationRecord);
    Language lookupLanguage(String targetLanguageCode);
    Word lookupWord(String word);
    void saveAcceptedLanguages(LanguageList languageList);
}
