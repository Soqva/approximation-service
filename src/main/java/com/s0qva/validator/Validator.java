package com.s0qva.validator;

public interface Validator {

    boolean isValid(ApproximationParameter parameterType, String ...parameters);

    boolean isValid(ApproximationParameter parameterType, String parameter);
}
