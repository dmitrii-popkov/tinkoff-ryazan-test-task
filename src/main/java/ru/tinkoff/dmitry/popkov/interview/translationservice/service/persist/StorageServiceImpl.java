package ru.tinkoff.dmitry.popkov.interview.translationservice.service.persist;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Language;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.TranslationRecord;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Word;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.WordTranslation;
import ru.tinkoff.dmitry.popkov.interview.translationservice.repository.LanguageRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.repository.TranslationRecordRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.repository.WordRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.repository.WordTranslationRepository;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StorageServiceImpl implements StorageService {

    private final LanguageRepository languageRepository;
    private final TranslationRecordRepository translationRecordRepository;
    private final WordRepository wordRepository;
    private final WordTranslationRepository wordTranslationRepository;

    @Override
    public void saveTranslation(TranslationRecord record) {
        List<WordTranslation> wordTranslations = record.getWords();
        wordTranslations.forEach((WordTranslation wordTranslation) -> {
            languageRepository.save(wordTranslation.getTranslateLanguage());
            wordRepository.save(wordTranslation.getSourceWord());
            wordRepository.save(wordTranslation.getTargetWord());
            wordTranslationRepository.save(wordTranslation);
        });
        translationRecordRepository.save(record);
    }
    @Override
    public Language lookupLanguage(String targetLanguageCode) {
        return languageRepository.findByCode(targetLanguageCode).stream().findAny()
                .orElse(Language.builder().code(targetLanguageCode).build());
    }

    @Override
    public Word lookupWord(String word) {
        return wordRepository.findByValue(word).stream().findAny()
                .orElse(Word.builder().value(word).build());
    }

    @Override
    public void saveAcceptedLanguages(LanguageList languageList) {
        languageList.getLanguages().stream()
                .map(languageDto -> Language.builder()
                        .code(languageDto.getCode())
                        .name(languageDto.getName()).build())
                .forEach(languageRepository::save);
    }
}
