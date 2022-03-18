package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate;

import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.TranslationDto;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.endpoint.out.TranslationResultDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextTokensProcessor {

    private static final String DELIMETERS = " ,!?;:.";
    private static final Pattern TOKENIZE_PATTERN = Pattern.compile(String.format("(?=[%s]+)", DELIMETERS));
    private static final Pattern MAP_PATTERN =
            Pattern.compile(String.format("([%s]*)(.*?)([%s]*)", DELIMETERS, DELIMETERS));
    private static final int DELIMETER_BEFORE_GROUP = 1;
    private static final int WORD_GROUP = 2;
    private static final int DELIMETER_AFTER_GROUP = 3;


    public TranslationResultDto mapWords(String text, UnaryOperator<String> wordsModifyOperator) {
        String[] splittedText = text.split(TOKENIZE_PATTERN.pattern());
        return Arrays.stream(splittedText)
                .parallel()
                .map(token -> transformWord(token, wordsModifyOperator))
                .collect(
                        () -> TranslationResultDto.builder().outputText("").translations(new ArrayList<>()).build(),
                        (resultDto, dto) -> resultDto.getTranslations().add(dto),
                        (dto, otherDto) -> dto.getTranslations().addAll(otherDto.getTranslations())
                );

    }

    private TranslationDto transformWord(String word, UnaryOperator<String> transformOperator) {
        TranslationDto.TranslationDtoBuilder dtoBuilder = TranslationDto.builder();
        Matcher matcher = MAP_PATTERN.matcher(word);
        if (matcher.matches()) {
            MatchResult matchResult = matcher.toMatchResult();
            String delimeterBefore = matchResult.group(DELIMETER_BEFORE_GROUP);
            String extractedWord = matchResult.group(WORD_GROUP);
            String delimeterAfter = matchResult.group(DELIMETER_AFTER_GROUP);
            dtoBuilder.delimeterBefore(delimeterBefore)
                    .delimeterAfter(delimeterAfter)
                    .sourceWord(extractedWord);
            if (!extractedWord.isEmpty()) {
                String transformedWord = applyTransformation(extractedWord, transformOperator);
                dtoBuilder.targetWord(transformedWord);
            } else {
                dtoBuilder.targetWord("");
            }
        }
        return dtoBuilder.build();
    }
    private String applyTransformation(String word, UnaryOperator<String> transformOperator) {
        return transformOperator.apply(word);
    }
}
