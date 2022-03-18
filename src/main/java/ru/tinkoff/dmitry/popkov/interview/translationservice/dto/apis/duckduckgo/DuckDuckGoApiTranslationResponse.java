package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.duckduckgo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DuckDuckGoApiTranslationResponse {
    @JsonProperty("detected_language")
    private String detectedLanguage;

    private String translated;
}
