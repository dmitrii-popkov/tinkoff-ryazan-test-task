package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YandexTranslationService implements TranslationService {

    private static final Pattern splitPattern = Pattern.compile("(?=[ .,])");
    // TODO: 3/16/22 REST errors handling

    private final YandexTranslator translator;

    @Setter(onMethod = @__(@Value("${services.translate.yandex.folder}")))
    private String cloudFolderId;

    @Override
    public TranslationResult translate(TranslationRequest translationRequest) {
        String targetLanguage = translationRequest.getTarget();
        String translatedText =
                Arrays.stream(translationRequest.getText().split(splitPattern.pattern()))
                .parallel()
                .map(word -> YandexApiTranslationRequest.builder()
                        .folderId(cloudFolderId).targetLanguageCode(targetLanguage)
                        .texts(List.of(word)).build())
                .map(translator::translate)
                .map(resp -> resp.getTranslations().get(0).getText())
                .collect(Collectors.joining(""));
        return TranslationResult.builder()
                .translatedText(translatedText).build();
    }

    @Override
    public LanguageList getAcceptedLanguages() {
        return translator.getAvailableLanguages(new YandexApiLanguagesRequest(cloudFolderId));
    }
}
