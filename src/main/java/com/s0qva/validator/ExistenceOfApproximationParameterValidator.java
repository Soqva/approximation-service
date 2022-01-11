package com.s0qva.validator;

import lombok.ToString;

import java.util.Arrays;

@ToString
public class ExistenceOfApproximationParameterValidator implements Validator {

    @Override
    public boolean isValid(ApproximationParameterType parameterType, String... parameters) {
        if (parameterType == ApproximationParameterType.NUMBER) {
            for (String parameter : parameters) {
                try {
                    Double.parseDouble(parameter);
                } catch (NumberFormatException exception) {
                    return false;
                }
            }
            return true;
        }

        return Arrays.stream(parameters)
                .noneMatch(parameter -> parameter == null || parameter.isBlank());
    }

    @Override
    public boolean isValid(ApproximationParameterType parameterType, String parameter) {
        if (parameterType == ApproximationParameterType.NUMBER) {
            try {
                Double.parseDouble(parameter);
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        return parameter != null && !parameter.isBlank();
    }
}
