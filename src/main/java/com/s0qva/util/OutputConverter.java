package com.s0qva.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OutputConverter {
    private final String OUTPUT_FORMAT = "%.2f";

    public List<String> convertDoubleListToOutputStringList(List<Double> list) {
        return list.stream()
                .map(value -> String.format(OUTPUT_FORMAT, value))
                .collect(Collectors.toList());
    }
}
