package ru.tinkoff.dmitry.popkov.interview.translationservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends CrudRepository<Language, Long> {
    List<Language> findByCode(String code);
}
