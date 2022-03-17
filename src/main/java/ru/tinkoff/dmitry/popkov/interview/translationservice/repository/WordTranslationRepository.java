package ru.tinkoff.dmitry.popkov.interview.translationservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.WordTranslation;

public interface WordTranslationRepository extends CrudRepository<WordTranslation, Long> {
}
