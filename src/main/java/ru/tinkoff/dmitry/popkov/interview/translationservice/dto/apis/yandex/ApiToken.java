package ru.tinkoff.dmitry.popkov.interview.translationservice.dto.apis.yandex;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Access;

@Data
@AllArgsConstructor
public class ApiToken {
    private String token;
}
