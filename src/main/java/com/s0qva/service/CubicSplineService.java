package com.s0qva.service;

import com.s0qva.dto.ApproximationResultDto;
import com.s0qva.util.ApproximationFaultCalculator;
import com.s0qva.util.OutputConverter;
import lombok.ToString;

@ToString
public final class CubicSplineService extends ApproximationService {
    private final double step = (getRightBorder() - getLeftBorder()) / getNumberOfGaps();

    public CubicSplineService(ApproximatedFunction approximatedFunction, double leftBorder, double rightBorder, int numberOfGaps) {
        super(approximatedFunction, leftBorder, rightBorder, numberOfGaps);
    }

    @Override
    public ApproximationResultDto calculateValueInterpolationPoint(double interpolationArgumentValue) {
        int index = findIndex(interpolationArgumentValue);
        double result = calculateAllTerm(index, interpolationArgumentValue);

        return buildApproximationResultDto(result, interpolationArgumentValue);
    }

    private double calculateAllTerm(int index, double interpolationArgumentValue) {
        return calculateFirstTerm(index, interpolationArgumentValue)
                + calculateSecondTerm(index, interpolationArgumentValue)
                + calculateThirdTerm(index, interpolationArgumentValue)
                + calculateFourthTerm(index, interpolationArgumentValue);
    }

    private double calculateFirstTerm(int index, double interpolationArgumentValue) {
        return getFunctionValues().get(index - 1)
                * Math.pow(interpolationArgumentValue - getArgumentValues().get(index), 2)
                * (2 * (interpolationArgumentValue - getArgumentValues().get(index - 1)) + step)
                / Math.pow(step, 3);
    }

    private double calculateSecondTerm(int index, double interpolationArgumentValue) {
        return getFunctionValues().get(index)
                * Math.pow((interpolationArgumentValue - getArgumentValues().get(index - 1)), 2)
                * (2 * (getArgumentValues().get(index) - interpolationArgumentValue) + step)
                / Math.pow(step, 3);
    }

    private double calculateThirdTerm(int index, double interpolationArgumentValue) {
        return findLowerMValue(index - 1)
                * Math.pow((interpolationArgumentValue - getArgumentValues().get(index)), 2)
                * (interpolationArgumentValue - getArgumentValues().get(index - 1))
                / Math.pow(step, 2);
    }

    private double calculateFourthTerm(int index, double interpolationArgumentValue) {
        return findLowerMValue(index)
                * Math.pow((interpolationArgumentValue - getArgumentValues().get(index - 1)), 2)
                * (interpolationArgumentValue - getArgumentValues().get(index))
                / Math.pow(step, 2);
    }

    private int findIndex(double interpolationArgumentValue) {
        int index = 1;

        for (int i = 1; i < getArgumentValues().size(); i++) {
            if (getArgumentValues().get(i - 1) <= interpolationArgumentValue && interpolationArgumentValue <= getArgumentValues().get(i)) {
                index = i;
                break;
            }
        }

        return index;
    }

    private double findLowerMValue(int index) {
        if (index == 0) {
            return 0;
        }

        if (index == getNumberOfGaps()) {
            return getApproximatedFunction().calculateDerivativeValue(getRightBorder());
        }

        return findLValue(index) * findLowerMValue(index + 1) + findUpperMValue(index);
    }

    private double findLValue(int index) {
        if (index == 0) {
            return 0;
        }

        return -1 / (findLValue(index - 1) + 4);
    }

    private double findCValue(int index) {
        return 3 * (getFunctionValues().get(index + 1) - getFunctionValues().get(index - 1))
                / ((getRightBorder() - getLeftBorder()) / getNumberOfGaps());
    }

    private double findUpperMValue(int index) {
        if (index == 0) {
            return 0;
        }

        return findLValue(index) * (findUpperMValue(index - 1) - findCValue(index));
    }

    private ApproximationResultDto buildApproximationResultDto(double result, double interpolationArgumentValue) {
        return ApproximationResultDto.builder()
                .argumentValues(OutputConverter.convertDoubleListToOutputStringList(getArgumentValues()))
                .functionValues(OutputConverter.convertDoubleListToOutputStringList(getFunctionValues()))
                .result(result)
                .absoluteFault(ApproximationFaultCalculator.calculateAbsoluteFault(getApproximatedFunction(), interpolationArgumentValue, result))
                .build();
    }
}
