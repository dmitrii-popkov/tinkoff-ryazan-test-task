package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.duckduckgo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.WordTranslator;

@Service
@Profile("dev")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DuckDuckGoWordTranslator implements WordTranslator {
    private final DuckDuckGoTranslator translator;

    @Override
    public String translate(String word, String targetLanguageCode) {
        return translator.translate(word, targetLanguageCode).getTranslated();
    }
}
