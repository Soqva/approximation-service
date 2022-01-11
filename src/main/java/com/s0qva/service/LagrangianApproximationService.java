package com.s0qva.service;

import com.s0qva.dto.ApproximationResultDto;
import com.s0qva.util.ApproximationFaultCalculator;
import com.s0qva.util.OutputConverter;
import lombok.ToString;

@ToString
public final class LagrangianApproximationService extends ApproximationService {

    public LagrangianApproximationService(ApproximatedFunction approximatedFunction, double leftBorder, double rightBorder, int polynomialDegree) {
        super(approximatedFunction, leftBorder, rightBorder, polynomialDegree);
    }

    @Override
    public ApproximationResultDto calculateValueInterpolationPoint(double interpolationArgumentValue) {
        double result = 0.;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            result += calculateLagrangePolynomialFraction(interpolationArgumentValue, i);
        }

        return buildApproximationResultDto(result, interpolationArgumentValue);
    }

    private double calculateLagrangePolynomialFraction(double interpolationArgumentValue, int skipPosition) {
        return calculateFractionPart(interpolationArgumentValue, skipPosition)
                * getFunctionValues().get(skipPosition)
                / calculateFractionPart(getArgumentValues().get(skipPosition), skipPosition);
    }

    private double calculateFractionPart(double value, int skipPosition) {
        double result = 1.;

        for (int i = 0; i < getNumberOfGaps(); i++) {
            if (i == skipPosition) {
                continue;
            }
            result *= value - getArgumentValues().get(i);
        }

        return result;
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
