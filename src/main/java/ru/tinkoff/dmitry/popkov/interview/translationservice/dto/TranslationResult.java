package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TranslationResult {
    private String translatedText;
}
