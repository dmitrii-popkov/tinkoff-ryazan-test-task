package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.duckduckgo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.*;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.duckduckgo.DuckDuckGoApiTranslationResponse;

@Service
@Profile("dev")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuckDuckGoTranslator {

    private final WebClient client;

    public DuckDuckGoApiTranslationResponse translate(String text, String targetLanguageCode) {
        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", "translate word")
                        .queryParam("vqd", "3-209471292243298217398241330070234284141-318046260352965599226810973816411707345")
                        .queryParam("to", targetLanguageCode)
                        .build()
                )
                .header("Content-Type", MediaType.TEXT_PLAIN_VALUE)
                .bodyValue(text)
                .retrieve()
                .toEntity(DuckDuckGoApiTranslationResponse.class)
                .block().getBody();
    }
    public LanguageList getAvailableLanguages() {
        throw new UnsupportedOperationException("Not supported by external API");
    }
}
