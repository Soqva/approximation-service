package com.s0qva.validator;

public interface Validator {

    boolean isValid(ApproximationParameterType parameterType, String ...parameters);

    boolean isValid(ApproximationParameterType parameterType, String parameter);
}
