package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Text {
    private List<Word> words;
}
