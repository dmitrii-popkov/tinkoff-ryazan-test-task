package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationRequest {
    private String target;
    private String text;
}
