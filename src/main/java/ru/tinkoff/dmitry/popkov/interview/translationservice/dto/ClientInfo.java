package ru.tinkoff.dmitry.popkov.interview.translationservice.dto;

import lombok.Builder;
import lombok.Data;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.WordTranslation;

import java.util.List;

@Data
@Builder
public class ClientInfo {
    private String ip;
    private List<WordTranslation> translations;
}
