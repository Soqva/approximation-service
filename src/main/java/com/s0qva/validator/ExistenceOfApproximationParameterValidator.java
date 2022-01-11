package com.s0qva.validator;

import lombok.ToString;

import java.util.Arrays;

@ToString
public class ExistenceOfApproximationParameterValidator implements Validator {

    @Override
    public boolean isValid(ApproximationParameterType parameterType, String... parameters) {
        if (parameterType == ApproximationParameterType.NUMBER) {
            return Arrays.stream(parameters)
                    .allMatch(parameter -> isValid(parameterType, parameter));
        }

        return Arrays.stream(parameters)
                .allMatch(parameter -> isValid(parameterType, parameter));
    }

    @Override
    public boolean isValid(ApproximationParameterType parameterType, String parameter) {
        if (parameterType == ApproximationParameterType.NUMBER) {
            try {
                Double.parseDouble(parameter);
            } catch (NumberFormatException exception) {
                return false;
            }
            return true;
        }

        return parameter != null && !parameter.isBlank();
    }
}
