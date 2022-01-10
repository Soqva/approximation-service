package com.s0qva.service;

import com.s0qva.dto.ApproximationResultDto;
import com.s0qva.util.ApproximationFaultCalculator;
import com.s0qva.util.OutputConverter;
import lombok.ToString;

import java.util.List;

@ToString
public final class LagrangianApproximationService extends ApproximationService {

    public LagrangianApproximationService(ApproximatedFunction approximatedFunction, double leftBorder, double rightBorder, int polynomialDegree) {
        super(approximatedFunction, leftBorder, rightBorder, polynomialDegree);
    }

    @Override
    public ApproximationResultDto calculateValueInterpolationPoint(double interpolationArgumentValue) {
        double result = 0.;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            result += calculateNumeratorLagrangePolynomial(interpolationArgumentValue, getArgumentValues(), i)
                    * getFunctionValues().get(i)
                    / calculateDenominatorLagrangePolynomial(getArgumentValues().get(i), getArgumentValues(), i);
        }

        return ApproximationResultDto.builder()
                .argumentValues(OutputConverter.convertDoubleListToOutputStringList(getArgumentValues()))
                .functionValues(OutputConverter.convertDoubleListToOutputStringList(getFunctionValues()))
                .result(result)
                .absoluteFault(ApproximationFaultCalculator.calculateAbsoluteFault(getApproximatedFunction(), interpolationArgumentValue, result))
                .build();
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
