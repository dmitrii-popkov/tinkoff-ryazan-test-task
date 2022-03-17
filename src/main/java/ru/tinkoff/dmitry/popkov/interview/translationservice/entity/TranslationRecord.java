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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TranslationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    private String inputText;

    private String targetText;

    private String ip;

    @ManyToMany
    @JoinTable(
            name = "TRANSLATION_RECORD_TRANSLATIONS",
            joinColumns = @JoinColumn(name = "TRANSLATION_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name="TRANSLATION_RECORD_ID", referencedColumnName = "ID")
    )
    private List<WordTranslation> words;
}
