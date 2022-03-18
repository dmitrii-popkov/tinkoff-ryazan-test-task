package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.in;

import lombok.Data;

@Data
public class TranslationRequest {
    private String target;
    private String text;
}
