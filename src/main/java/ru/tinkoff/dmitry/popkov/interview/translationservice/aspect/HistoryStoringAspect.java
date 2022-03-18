package ru.tinkoff.dmitry.popkov.interview.translationservice.aspect;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.ClientInfo;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.in.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.TranslationResultDto;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Language;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.TranslationRecord;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.WordTranslation;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.persist.StorageService;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoryStoringAspect{

    private final StorageService storageService;

    @Setter(onMethod = @__(@Autowired))
    private ClientInfo clientInfo;

    @AfterReturning(value = "execution(* ru.tinkoff.dmitry.popkov.interview.translationservice" +
            ".service.translate.TranslationService.translate(..)) && args(translationRequest)", returning = "translationResult",
            argNames = "joinPoint,translationRequest,translationResult")
    public void persistTranslation(JoinPoint joinPoint, TranslationRequest translationRequest,
                            TranslationResultDto translationResult) {
        Language language = storageService.lookupLanguage(translationResult.getLanguageCode());
        List<WordTranslation> translationEntities = translationResult.getTranslations().stream().map(
                dto -> WordTranslation.builder()
                        .sourceWord(storageService.lookupWord(dto.getSourceWord()))
                        .targetWord(storageService.lookupWord(dto.getTargetWord()))
                        .translateLanguage(language)
                .build()
        ).collect(Collectors.toList());
        TranslationRecord translationRecord = TranslationRecord.builder()
                .inputText(translationRequest.getText())
                .targetText(translationResult.getOutputText())
                .ip(clientInfo.getIp())
                .words(translationEntities)
                .build();
        storageService.saveTranslation(translationRecord);

    }

    @AfterReturning(value = "execution(* ru.tinkoff.dmitry.popkov.interview.translationservice" +
            ".service.translate.TranslationService.getAcceptedLanguages())",
            returning = "languageList")
    public void persistLanguages(JoinPoint joinPoint, LanguageList languageList) {
        storageService.saveAcceptedLanguages(languageList);
    }

}
