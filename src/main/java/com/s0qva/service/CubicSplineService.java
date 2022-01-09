package com.s0qva.service;


import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public final class CubicSplineService extends ApproximationService {
    private final double step = (getRightBorder() - getLeftBorder()) / getN();

    public CubicSplineService(double leftBorder, double rightBorder, double numberParts, String function) {
        super(leftBorder, rightBorder, numberParts, function);
    }

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
        return getyValues().get(index - 1) * Math.pow(interpolationXPoint - getxValues().get(index), 2)
                * (2 * (interpolationXPoint - getxValues().get(index - 1)) + step)
                / Math.pow(step, 3);
    }

    private double calculateSecondTerm(int index, double interpolationXPoint) {
        return getyValues().get(index) * Math.pow((interpolationXPoint - getxValues().get(index - 1)), 2)
                * (2 * (getxValues().get(index) - interpolationXPoint) + step)
                / Math.pow(step, 3);
    }

    private double calculateThirdTerm(int index, double interpolationXPoint) {
        return findLowerMValue(index - 1) * Math.pow((interpolationXPoint - getxValues().get(index)), 2)
                * (interpolationXPoint - getxValues().get(index - 1))
                / Math.pow(step, 2);
    }

    private double calculateFourthTerm(int index, double interpolationXPoint) {
        return findLowerMValue(index) * Math.pow((interpolationXPoint - getxValues().get(index - 1)), 2)
                * (interpolationXPoint - getxValues().get(index))
                / Math.pow(step, 2);
    }

    private int findIndex(double interpolationXPoint) {
        int index = 1;

        for (int i = 1; i < getxValues().size(); i++) {
            if (getxValues().get(i - 1) <= interpolationXPoint && interpolationXPoint <= getxValues().get(i)) {
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

        if (index == getN()) {
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
        return 3 * (getyValues().get(index + 1) - getyValues().get(index - 1))
                / ((getRightBorder() - getLeftBorder()) / getN());
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

    @Override
    public double calculateFunctionValue(double argument) {
        Argument xArgument = new Argument("x = " + argument);
        return new Expression("function(x)", getFunction(), xArgument).calculate();
    }
}
