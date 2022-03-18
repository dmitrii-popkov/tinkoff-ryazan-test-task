package ru.tinkoff.dmitry.popkov.interview.translationservice.service.translate;

import org.springframework.stereotype.Service;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationDto;
import ru.tinkoff.dmitry.popkov.interview.translationservice.dto.TranslationResultDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.UnaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TextTokensProcessor {

    private static final String DELIMETERS = " ,!?;:.";
    private static final Pattern TOKENIZE_PATTERN = Pattern.compile(String.format("(?=[%s]+)", DELIMETERS));
    private static final Pattern MAP_PATTERN =
            Pattern.compile(String.format("([%s]*)(.*?)([%s]*)", DELIMETERS, DELIMETERS));


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
            String delimeterBefore = matchResult.group(1);
            String extractedWord = matchResult.group(2);
            String delimeterAfter = matchResult.group(3);
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
