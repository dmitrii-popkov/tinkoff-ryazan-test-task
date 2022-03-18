package ru.tinkoff.dmitry.popkov.interview.translationservice.aspect;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.ClientInfo;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.TranslationRecord;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.WordTranslation;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.persist.StorageService;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoryStoringAspect {

    private final StorageService storageService;

    @Setter(onMethod = @__(@Autowired))
    private ClientInfo clientInfo;

    private final List<WordTranslation> translations = new ArrayList<>();

    @AfterReturning(value = "execution(* ru.tinkoff.dmitry.popkov.interview.translationservice" +
            ".service.translate.TranslationService.translate(..)) && args(translationRequest)", returning = "translationResult",
            argNames = "joinPoint,translationRequest,translationResult")
    public void persistTranslation(JoinPoint joinPoint, TranslationRequest translationRequest,
                            TranslationResult translationResult) {
        TranslationRecord translationRecord = TranslationRecord.builder()
                .inputText(translationRequest.getText())
                .targetText(translationResult.getTranslatedText())
                .ip(clientInfo.getIp())
                .words(translations)
                .build();
        storageService.saveTranslation(translationRecord);
        translations.clear();

    }

    @AfterReturning(value = "execution(* ru.tinkoff.dmitry.popkov.interview.translationservice" +
            ".service.translate.WordTranslator.translate(..)) && args(word, targetLanguageCode)",
            returning = "targetWord",
            argNames = "joinPoint,word,targetLanguageCode,targetWord")
    public void prepareTranslationWords(JoinPoint joinPoint, String word,
                                        String targetLanguageCode, String targetWord) {
        WordTranslation newTranslation = WordTranslation.builder()
                .sourceWord(storageService.lookupWord(word))
                .targetWord(storageService.lookupWord(targetWord))
                .translateLanguage(storageService.lookupLanguage(targetLanguageCode))
                .build();
        translations.add(newTranslation);
    }
    @AfterReturning(value = "execution(* ru.tinkoff.dmitry.popkov.interview.translationservice" +
            ".service.translate.TranslationService.getAcceptedLanguages())",
            returning = "languageList")
    public void persistLanguages(JoinPoint joinPoint, LanguageList languageList) {
        storageService.saveAcceptedLanguages(languageList);
    }

}
