package ru.tinkoff.dmitry.popkov.interview.translationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.ApiToken;

@Configuration
public class YandexClientConfiguration {
    @Bean
    @Profile("prod")
    public WebClient getYandexClient(
            @Value("${services.translate.yandex.url}") String url
    ) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }
    @Bean
    public ApiToken yandexIamToken(
            @Value("${services.translate.yandex.authorization}") String defaultToken) {
        return new ApiToken(defaultToken);
    }
}
