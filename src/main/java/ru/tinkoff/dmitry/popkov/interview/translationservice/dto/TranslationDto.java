package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationDto {
    private String delimeterBefore;
    private String sourceWord;
    private String delimeterAfter;
    private String targetWord;
}
