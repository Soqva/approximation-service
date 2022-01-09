package com.s0qva.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@ToString
@EqualsAndHashCode
public abstract class ApproximationService {
    private final List<Double> argumentValues;
    private final List<Double> functionValues;
    private final Function function;
    private final double leftBorder;
    private final double rightBorder;
    private final int numberOfGaps;

    public ApproximationService(double leftBorder, double rightBorder, int numberOfGaps, String function) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.numberOfGaps = numberOfGaps;
        this.function = new Function("function(x) = " + function);
        this.argumentValues = createArgumentValues();
        this.functionValues = createFunctionValues();
    }

    public abstract double calculateValueInterpolationPoint(double interpolationArgumentValue);

    public double calculateFunctionValue(double argumentValue) {
        Argument argument = new Argument("x = " + argumentValue);
        return new Expression("function(x)", getFunction(), argument).calculate();
    }

    private List<Double> createArgumentValues() {
        double step = (rightBorder - leftBorder) / numberOfGaps;

        return Stream.iterate(leftBorder, currentArgumentValue -> currentArgumentValue + step)
                .limit(numberOfGaps + 1)
                .collect(Collectors.toList());
    }

    private List<Double> createFunctionValues() {
        return argumentValues.stream()
                .map(this::calculateFunctionValue)
                .collect(Collectors.toList());
    }
}
