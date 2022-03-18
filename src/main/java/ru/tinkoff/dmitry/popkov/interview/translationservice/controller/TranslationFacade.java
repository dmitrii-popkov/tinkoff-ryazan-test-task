package ru.tinkoff.dmitry.popkov.interview.translationservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResultDto;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.persist.StorageService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TranslationService;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TranslationFacade {

    private final TranslationService translationService;

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void apiErrorHandler() {

    }

    @GetMapping("/available")
    public ResponseEntity<LanguageList> getSupportedLanguages() {
        return ResponseEntity.ok(translationService.getAcceptedLanguages());
    }
    @PostMapping("/translate")
    public ResponseEntity<TranslationResultDto> translate(@RequestBody TranslationRequest request) {
        TranslationResultDto result = translationService.translate(request);
        return ResponseEntity.ok(result);
    }
}
