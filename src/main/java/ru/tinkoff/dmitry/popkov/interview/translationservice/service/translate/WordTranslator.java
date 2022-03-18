package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate;

public interface WordTranslator {
    String translate(String word, String languageCode);
}
