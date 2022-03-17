package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class LanguageList {
    private List<LanguageDto> languages;
}
