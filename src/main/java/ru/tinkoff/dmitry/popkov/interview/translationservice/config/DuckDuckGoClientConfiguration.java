package ru.tinkoff.dmitry.popkov.interview.translationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DuckDuckGoClientConfiguration {
    @Bean
    @Profile("dev")
    public WebClient getDuckDuckGoClient(
            @Value("${services.translate.duckduckgo.url}") String url
    ) {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}
