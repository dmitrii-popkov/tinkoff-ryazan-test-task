package ru.tinkoff.dmitry.popkov.interview.translationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class WordTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private Language translateLanguage;

    @ManyToOne
    @JoinColumn(name = "SOURCE_WORD_ID")
    private Word sourceWord;

    @ManyToOne
    @JoinColumn(name = "TARGET_WORD_ID")
    private Word targetWord;
}
