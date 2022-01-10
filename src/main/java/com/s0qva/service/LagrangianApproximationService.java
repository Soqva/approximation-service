package com.s0qva.service;

import lombok.ToString;

import java.util.List;

@ToString
public final class LagrangianApproximationService extends ApproximationService {

    public LagrangianApproximationService(double leftBorder, double rightBorder, int polynomialDegree, String function) {
        super(leftBorder, rightBorder, polynomialDegree, function);
    }

    @Override
    public double calculateValueInterpolationPoint(double interpolationArgumentValue) {
        double result = 0.;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            result += calculateNumeratorLagrangePolynomial(interpolationArgumentValue, getArgumentValues(), i)
                    * getFunctionValues().get(i)
                    / calculateDenominatorLagrangePolynomial(getArgumentValues().get(i), getArgumentValues(), i);
        }

        return result;
    }

    private double calculateNumeratorLagrangePolynomial(double interpolationArgumentValue, List<Double> argumentValues, int skipPosition) {
        double result = 1.;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= interpolationArgumentValue - argumentValues.get(i);
        }

        return result;
    }

    private double calculateDenominatorLagrangePolynomial(double kPoint, List<Double> argumentValues, int skipPosition) {
        double result = 1.;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= kPoint - argumentValues.get(i);
        }

        return result;
    }
}
