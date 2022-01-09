package com.s0qva.service;


import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public final class CubicSplineService extends ApproximationService {
    private final double step = (getRightBorder() - getLeftBorder()) / getNumberOfGaps();

    public CubicSplineService(double leftBorder, double rightBorder, int numberParts, String function) {
        super(leftBorder, rightBorder, numberParts, function);
    }

    @Override
    public double calculateValueInterpolationPoint(double interpolationXPoint) {
        int index = findIndex(interpolationXPoint);
        double result;

        result = calculateFirstTerm(index, interpolationXPoint)
                + calculateSecondTerm(index, interpolationXPoint)
                + calculateThirdTerm(index, interpolationXPoint)
                + calculateFourthTerm(index, interpolationXPoint);

        return result;
    }

    private double calculateFirstTerm(int index, double interpolationXPoint) {
        return getFunctionValues().get(index - 1) * Math.pow(interpolationXPoint - getArgumentValues().get(index), 2)
                * (2 * (interpolationXPoint - getArgumentValues().get(index - 1)) + step)
                / Math.pow(step, 3);
    }

    private double calculateSecondTerm(int index, double interpolationXPoint) {
        return getFunctionValues().get(index) * Math.pow((interpolationXPoint - getArgumentValues().get(index - 1)), 2)
                * (2 * (getArgumentValues().get(index) - interpolationXPoint) + step)
                / Math.pow(step, 3);
    }

    private double calculateThirdTerm(int index, double interpolationXPoint) {
        return findLowerMValue(index - 1) * Math.pow((interpolationXPoint - getArgumentValues().get(index)), 2)
                * (interpolationXPoint - getArgumentValues().get(index - 1))
                / Math.pow(step, 2);
    }

    private double calculateFourthTerm(int index, double interpolationXPoint) {
        return findLowerMValue(index) * Math.pow((interpolationXPoint - getArgumentValues().get(index - 1)), 2)
                * (interpolationXPoint - getArgumentValues().get(index))
                / Math.pow(step, 2);
    }

    private int findIndex(double interpolationXPoint) {
        int index = 1;

        for (int i = 1; i < getArgumentValues().size(); i++) {
            if (getArgumentValues().get(i - 1) <= interpolationXPoint && interpolationXPoint <= getArgumentValues().get(i)) {
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
            return calculateDerivativeValue(getRightBorder());
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

    private double calculateDerivativeValue(double argument) {
        Argument xArgument = new Argument("x = " + argument);
        return new Expression(String.format("der(%s, x)", getFunction().getFunctionExpressionString()), xArgument).calculate();
    }
}
