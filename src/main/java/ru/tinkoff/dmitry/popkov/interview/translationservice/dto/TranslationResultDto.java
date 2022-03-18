package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TranslationResultDto {
    private String inputText;
    private String outputText;
    private String languageCode;
    private List<TranslationDto> translations;
}
