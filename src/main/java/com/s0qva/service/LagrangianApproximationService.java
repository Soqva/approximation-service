package com.s0qva.service;

import java.util.List;

public final class LagrangianApproximationService extends ApproximationService {

    public LagrangianApproximationService(double leftBorder, double rightBorder, int polynomialDegree, String function) {
        super(leftBorder, rightBorder, polynomialDegree, function);
    }

    @Override
    public double calculateValueInterpolationPoint(double interpolationXPoint) {
        double result = 0.0;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            result += calculateNumeratorLagrangePolynomial(interpolationXPoint, getArgumentValues(), i) * getFunctionValues().get(i)
                    / calculateDenominatorLagrangePolynomial(getArgumentValues().get(i), getArgumentValues(), i);
        }

        return result;
    }

    private double calculateNumeratorLagrangePolynomial(double interpolationXPoint, List<Double> values, int skipPosition) {
        double result = 1.0;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= interpolationXPoint - values.get(i);
        }

        return result;
    }

    private double calculateDenominatorLagrangePolynomial(double kPoint, List<Double> values, int skipPosition) {
        double result = 1.0;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= kPoint - values.get(i);
        }

        return result;
    }

   /* @Override
    public double calculateFunctionValue(double argumentValue) {
        Argument xArgument = new Argument("x = " + argumentValue);
        return new Expression("function(x)", getFunction(), xArgument).calculate();
    }*/
}
