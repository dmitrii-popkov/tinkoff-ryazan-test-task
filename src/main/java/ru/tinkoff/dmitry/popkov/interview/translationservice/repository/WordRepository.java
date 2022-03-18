package ru.tinkoff.dmitry.popkov.interview.translationservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.Word;

import java.util.List;

public interface WordRepository extends CrudRepository<Word, Long> {
    List<Word> findByValue(String value);
}
