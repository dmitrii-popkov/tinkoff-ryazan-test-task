package ru.tinkoff.dmitry.popkov.interview.translationservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.StorageService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.TranslationService;

@RestController("/api/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TranslationFacade {

    private final TranslationService translationService;
    private final StorageService storageService;


    // TODO: 3/16/22 Exception handling

    @PostMapping
    public ResponseEntity<TranslationResult> translate(TranslationRequest request) {
        TranslationResult result = translationService.translate(request);
        storageService.saveTranslation(request, result);
        return ResponseEntity.ok(result);
    }
}
