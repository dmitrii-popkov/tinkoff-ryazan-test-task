package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TranslationRequest {
    private String target;
    private String text;
}
