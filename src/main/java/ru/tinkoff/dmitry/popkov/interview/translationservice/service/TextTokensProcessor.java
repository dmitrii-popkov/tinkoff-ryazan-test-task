package ru.tinkoff.dmitry.popkov.interview.translationservice.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TextTokensProcessor {

    private static final String DELIMETERS = " ,!?;:.";
    private static final Pattern TOKENIZE_PATTERN = Pattern.compile(String.format("(?=[%s]+)", DELIMETERS));
    private static final Pattern MAP_PATTERN =
            Pattern.compile(String.format("([%s]*)(.*?)([%s]*)", DELIMETERS, DELIMETERS));

    public String mapWords(String text, UnaryOperator<String> wordsModifyOperator) {
        String[] splittedText = text.split(TOKENIZE_PATTERN.pattern());

        return Arrays.stream(splittedText).parallel()
                .map(token -> transformWord(token, wordsModifyOperator))
                .collect(Collectors.joining(""));
    }
    private String transformWord(String word, UnaryOperator<String> transformOperator) {
        String resultWord = word;
        Matcher matcher = MAP_PATTERN.matcher(word);
        if (matcher.matches()) {
            MatchResult matchResult = matcher.toMatchResult();
            String delimeterBefore = matchResult.group(1);
            String extractedWord = matchResult.group(2);
            String delimeterAfter = matchResult.group(3);
            if (!extractedWord.isEmpty()) {
                resultWord = delimeterBefore + applyTransformation(extractedWord, transformOperator) + delimeterAfter;
            }
        }
        return resultWord;
    }
    private String applyTransformation(String word, UnaryOperator<String> transformOperator) {
        return transformOperator.apply(word);
    }
}
