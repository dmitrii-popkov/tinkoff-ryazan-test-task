package ru.tinkoff.dmitry.popkov.interview.translationservice.entity;

import lombok.*;

import javax.persistence.*;

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
