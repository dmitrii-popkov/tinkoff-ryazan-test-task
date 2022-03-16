package ru.tinkoff.dmitry.popkov.interview.translationservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.StorageService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.TranslationService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TranslationFacade {

    private final TranslationService translationService;
    private final StorageService storageService;


    // TODO: 3/16/22 Exception handling

    @GetMapping("/available")
    public ResponseEntity<LanguageList> getSupportedLanguages() {
        return ResponseEntity.ok(translationService.getAcceptedLanguages());
    }
    @PostMapping("/translate")
    public ResponseEntity<TranslationResult> translate(TranslationRequest request) {
        TranslationResult result = translationService.translate(request);
        storageService.saveTranslation(request, result);
        return ResponseEntity.ok(result);
    }
}