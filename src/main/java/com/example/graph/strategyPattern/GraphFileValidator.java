package com.example.graph.strategyPattern;

import java.util.List;

public abstract class GraphFileValidator {
    abstract public Boolean isFormatCorrect(List<List<String>> graphAsStrings);
}
