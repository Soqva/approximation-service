package com.s0qva.servlet;

import com.s0qva.service.CubicSplineService;
import com.s0qva.service.LagrangianApproximationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/app")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String leftBorder = req.getParameter("leftBorder");
        final String rightBorder = req.getParameter("rightBorder");
        final String n = req.getParameter("n");
        final String interpolationPoint = req.getParameter("interpolationPoint");
        final String function = req.getParameter("function");

        LagrangianApproximationService lagrangianApproximationService = new LagrangianApproximationService(
                Double.parseDouble(leftBorder),
                Double.parseDouble(rightBorder),
                Integer.parseInt(n),
                function
        );

        final List<Double> xValues = lagrangianApproximationService.getxValues().stream()
                .map(xValue -> Math.round(xValue * 100.0) / 100.0)
                .collect(Collectors.toList());

        final List<Double> yValues = lagrangianApproximationService.getyValues().stream()
                .map(yValue -> Math.round(yValue * 100.0) / 100.0)
                .collect(Collectors.toList());

        req.setAttribute("xValues", xValues);
        req.setAttribute("yValues", yValues);

        final double lagrangianResult = lagrangianApproximationService
                .calculateValueInterpolationPoint(Double.parseDouble(interpolationPoint));
        req.setAttribute("lagrangianResult", lagrangianResult);

        double point = Double.parseDouble(interpolationPoint);
        double lagrangianAbsoluteFault = Math.abs(lagrangianApproximationService.calculateFunctionValue(point)
                - lagrangianResult);

        req.setAttribute("lagrangianAbsoluteFault", lagrangianAbsoluteFault);

        CubicSplineService cubicSplineService = new CubicSplineService(
                Double.parseDouble(leftBorder),
                Double.parseDouble(rightBorder),
                Integer.parseInt(n),
                function
        );

        final double cubicResult = cubicSplineService
                .calculateValueInterpolationPoint(Double.parseDouble(interpolationPoint));
        req.setAttribute("cubicResult", cubicResult);

        double cubicAbsoluteFault = Math.abs(cubicSplineService.calculateFunctionValue(point)
                - cubicResult);

        req.setAttribute("cubicAbsoluteFault", cubicAbsoluteFault);

        req.setAttribute("n", Integer.parseInt(n) + 2);

        req.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(req, resp);
    }
}
