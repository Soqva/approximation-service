package com.s0qva.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

@Getter
@ToString
@EqualsAndHashCode
public class ApproximatedFunction {
    private final Function approximatedFunction;

    public ApproximatedFunction(String approximatedFunction) {
        this.approximatedFunction = new Function("function(x) = " + approximatedFunction);
    }

    public double calculateFunctionValue(double argumentValue) {
        return approximatedFunction.calculate(argumentValue);
    }

    public double calculateDerivativeValue(double argumentValue) {
        Argument argument = new Argument("x = " + argumentValue);
        return new Expression(String.format("der(%s, x)", approximatedFunction.getFunctionExpressionString()), argument).calculate();
    }
}
