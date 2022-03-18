package ru.tinkoff.dmitry.popkov.interview.translationservice.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex.ApiToken;

@Configuration
public class YandexTokenStoreConfig {

    @Setter(onMethod = @__(@Autowired))
    private ApiToken yandexToken;

    @Scheduled(cron = "0 12 * * ?")
    public void updateToken(@Value("${services.translate.yandex.auth-token") String oauthToken) {
        String token = WebClient.builder()
                .baseUrl("https://iam.api.cloud.yandex.net/iam/v1/tokens")
                .build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("yandexPassportOauthToken", oauthToken)
                .build())
                .retrieve()
                .toEntity(YandexApiTokenResponse.class)
                .block()
                .getBody().getIamToken();
        yandexToken.setToken(String.format("Bearer %s", token));
    }
}
