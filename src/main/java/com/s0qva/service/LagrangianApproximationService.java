package com.s0qva.service;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.List;

public final class LagrangianApproximationService extends ApproximationService {


    public LagrangianApproximationService(double leftBorder, double rightBorder, double polynomialDegree, String function) {
        super(leftBorder, rightBorder, polynomialDegree, function);
    }

    public double calculateValueInterpolationPoint(double interpolationXPoint) {
        double result = 0.0;

        for (int i = 0; i < getN(); i++) {
            result += calculateNumeratorLagrangePolynomial(interpolationXPoint, getxValues(), i) * getyValues().get(i)
                    / calculateDenominatorLagrangePolynomial(getxValues().get(i), getxValues(), i);
        }

        return result;
    }

    private double calculateNumeratorLagrangePolynomial(double interpolationXPoint, List<Double> values, int skipPosition) {
        double result = 1.0;

        for (int i = 0; i < getN(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= interpolationXPoint - values.get(i);
        }

        return result;
    }

    private double calculateDenominatorLagrangePolynomial(double kPoint, List<Double> values, int skipPosition) {
        double result = 1.0;

        for (int i = 0; i < getN(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= kPoint - values.get(i);
        }

        return result;
    }

    @Override
    public double calculateFunctionValue(double argument) {
        Argument xArgument = new Argument("x = " + argument);
        return new Expression("function(x)", getFunction(), xArgument).calculate();
    }
}
