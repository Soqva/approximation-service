package com.s0qva.util;

import com.s0qva.service.ApproximatedFunction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApproximationFaultCalculator {

    public double calculateAbsoluteFault(ApproximatedFunction approximatedFunction, double interpolationArgumentValue, double approximationServiceResult) {
        return Math.abs(approximatedFunction.calculateFunctionValue(interpolationArgumentValue) - approximationServiceResult);
    }
}
