package ru.tinkoff.dmitry.popkov.interview.translationservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.dmitry.popkov.interview.translationservice.entity.TranslationRecord;

public interface TranslationRecordRepository extends CrudRepository<TranslationRecord, Long> {
}
