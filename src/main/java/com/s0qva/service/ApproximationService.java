package com.s0qva.service;

import com.s0qva.dto.ApproximationResultDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@ToString
@EqualsAndHashCode
public abstract class ApproximationService {
    private final List<Double> argumentValues;
    private final List<Double> functionValues;
    private final double leftBorder;
    private final double rightBorder;
    private final int numberOfGaps;
    private final ApproximatedFunction approximatedFunction;

    public ApproximationService(ApproximatedFunction approximatedFunction, double leftBorder, double rightBorder, int numberOfGaps) {
        this.approximatedFunction = approximatedFunction;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.numberOfGaps = numberOfGaps;
        this.argumentValues = createArgumentValues();
        this.functionValues = createFunctionValues();
    }

    public abstract ApproximationResultDto calculateValueInterpolationPoint(double interpolationArgumentValue);

    private List<Double> createArgumentValues() {
        double step = (rightBorder - leftBorder) / numberOfGaps;

        return Stream.iterate(leftBorder, currentArgumentValue -> currentArgumentValue + step)
                .limit(numberOfGaps + 1)
                .collect(Collectors.toList());
    }

    private List<Double> createFunctionValues() {
        return argumentValues.stream()
                .map(approximatedFunction::calculateFunctionValue)
                .collect(Collectors.toList());
    }
}
