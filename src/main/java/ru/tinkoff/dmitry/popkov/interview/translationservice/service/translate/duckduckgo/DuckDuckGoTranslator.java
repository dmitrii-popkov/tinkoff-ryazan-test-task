package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate.duckduckgo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.LanguageList;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.duckduckgo.DuckDuckGoApiTranslationResponse;

@Service
@Profile("dev")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DuckDuckGoTranslator {

    private static final String QUERY_PARAM = "query";
    private static final String KEY_PARAM = "vqd";
    private static final String LANGUAGE_PARAM = "to";
    private static final String CONTENT_TYPE = "Content-Type";

    private final WebClient client;

    @Value("${services.translate.duckduckgo.vqd}")
    private String vqd;
    @Value("${services.translate.duckduckgo.query}")
    private String query;

    public DuckDuckGoApiTranslationResponse translate(String text, String targetLanguageCode) {
        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(QUERY_PARAM, query)
                        .queryParam(KEY_PARAM, vqd)
                        .queryParam(LANGUAGE_PARAM, targetLanguageCode)
                        .build()
                )
                .header(CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .bodyValue(text)
                .retrieve()
                .toEntity(DuckDuckGoApiTranslationResponse.class)
                .block().getBody();
    }
    public LanguageList getAvailableLanguages() {
        throw new UnsupportedOperationException("Not supported by external API");
    }
}
