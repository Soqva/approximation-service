package com.s0qva.validator;

import lombok.ToString;

import java.util.Arrays;

@ToString
public class ApproximationParametersValidator implements Validator {

    @Override
    public boolean isValid(ApproximationParameter parameterType, String... parameters) {
        if (parameterType == ApproximationParameter.NUMBER) {
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
    public boolean isValid(ApproximationParameter parameterType, String parameter) {
        if (parameterType == ApproximationParameter.NUMBER) {
            try {
                Double.parseDouble(parameter);
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        return parameter != null && !parameter.isBlank();
    }
}
