package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationResult {
    @JsonUnwrapped
    private String translatedText;
}
