package ru.tinkoff.dmitry.popkov.interview.translationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.ClientInfo;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    // TODO: 3/17/22 Call yc iam create-token

    @Bean
    @Profile("prod")
    public WebClient getYandexClient(
            @Value("${services.translate.yandex.url}") String url,
            @Value("${services.translate.yandex.authorization}") String token
    ) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add("Authorization", token);
                })
                .build();
    }
    @Bean
    @Profile("dev")
    public WebClient getDuckDuckGoClient(
            @Value("${services.translate.duckduckgo.url}") String url
    ) {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    @RequestScope
    public ClientInfo getIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return ClientInfo.builder().ip(remoteAddr).build();
    }
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }
}
