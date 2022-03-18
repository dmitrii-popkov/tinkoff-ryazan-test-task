package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.yandex;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.YandexApiTranslationRequest;
import ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.WordTranslator;

import java.util.List;

@Service
@Profile("prod")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YandexWordTranslator implements WordTranslator, ApplicationContextAware {

    private final YandexTranslator translator;
    @Getter
    private ApplicationContext context;

    @Setter(onMethod = @__(@Value("${services.translate.yandex.folder}")))
    private String cloudFolderId;
    @Override
    public String translate(String word, String languageCode) {
        YandexApiTranslationRequest request = YandexApiTranslationRequest.builder()
                .folderId(cloudFolderId).targetLanguageCode(languageCode)
                .texts(List.of(word)).build();
        return translator.translate(request).getTranslations().get(0).getText();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
