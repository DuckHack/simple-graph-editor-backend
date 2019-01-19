package com.example.graph.strategyPattern;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class SimpleGraphValidator extends GraphFileValidator {
    @Override
    public Boolean isFormatCorrect(List<List<String>> graphAsStrings) {
        String patternString = "(\\s|^)\\d+(\\s|$)";
        Pattern pattern = Pattern.compile(patternString);

        Boolean isFileContainsOnlyDigits = graphAsStrings.stream()
                .allMatch(l -> l.stream()
//                        .allMatch(s -> s.chars().allMatch( Character::isDigit)) );
                        .allMatch(s -> pattern.matcher(s).matches()));

        if (!isFileContainsOnlyDigits) {
            return false;
        }

        Boolean isFirstLineContainVertexesNumber = graphAsStrings.get(0).size() == 1;

        if (!isFirstLineContainVertexesNumber) {
            return false;
        }

        Boolean isGivenGraphSizeCorrect = Integer.parseInt(graphAsStrings.get(0).get(0)) == graphAsStrings.size() - 1;

        if (!isGivenGraphSizeCorrect) {
            return false;
        }

        return graphAsStrings.stream().skip(1)
                .allMatch(l -> l.stream().skip(1).limit(1)
                        .allMatch(s -> Integer.parseInt(s) == l.size() - 2));
    }

}
