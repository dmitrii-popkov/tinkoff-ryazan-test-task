package ru.tinkoff.dmitry.popkov.interview.translationservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResult;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.persist.StorageService;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.TranslationService;

@RestController
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
    public ResponseEntity<TranslationResult> translate(@RequestBody TranslationRequest request) {
        TranslationResult result = translationService.translate(request);
//        storageService.saveTranslation(request, result);
        return ResponseEntity.ok(result);
    }
}
