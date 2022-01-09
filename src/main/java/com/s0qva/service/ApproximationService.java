package com.s0qva.service;

import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ApproximationService {
    private final double leftBorder;
    private final double rightBorder;
    private final double n;
    private final List<Double> xValues;
    private final List<Double> yValues;
    private final Function function;

    public ApproximationService(double leftBorder, double rightBorder, double n, String function) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.n = n;
        this.function = new Function("function(x) = " + function);
        this.xValues = createXValues();
        this.yValues = createYValues(xValues);
    }

    public abstract double calculateFunctionValue(double argument);

    private List<Double> createXValues() {
        List<Double> xValues = new ArrayList<>();
        double step = (rightBorder - leftBorder) / n;

        xValues.add(leftBorder);

        for (int i = 1; i <= n; i++) {
            Double previousValue = xValues.get(i - 1);
            xValues.add(previousValue + step);
        }

        return xValues;
    }

    private List<Double> createYValues(List<Double> xValues) {
        return xValues.stream()
                .map(this::calculateFunctionValue)
                .collect(Collectors.toList());
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public double getN() {
        return n;
    }

    public List<Double> getxValues() {
        return xValues;
    }

    public List<Double> getyValues() {
        return yValues;
    }

    public Function getFunction() {
        return function;
    }
}
