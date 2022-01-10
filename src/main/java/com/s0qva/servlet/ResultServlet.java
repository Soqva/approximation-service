package com.s0qva.servlet;

import com.s0qva.dto.ApproximationResultDto;
import com.s0qva.service.ApproximationService;
import com.s0qva.service.CubicSplineService;
import com.s0qva.service.LagrangianApproximationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {
    private ApproximationService lagrangianApproximationService;
    private ApproximationService cubicSplineService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getParameter("function");
        double interpolationPoint = Double.parseDouble(req.getParameter("interpolationPoint"));
        double leftBorder = Double.parseDouble(req.getParameter("leftBorder"));
        double rightBorder = Double.parseDouble(req.getParameter("rightBorder"));
        int numberOfGaps = Integer.parseInt(req.getParameter("numberOfGaps"));

        lagrangianApproximationService = new LagrangianApproximationService(leftBorder, rightBorder, numberOfGaps, function);
        cubicSplineService = new CubicSplineService(leftBorder, rightBorder, numberOfGaps, function);

        ApproximationResultDto lagrangianResultDto = lagrangianApproximationService.calculateValueInterpolationPoint(interpolationPoint);
        ApproximationResultDto cubicResultDto = cubicSplineService.calculateValueInterpolationPoint(interpolationPoint);

        req.setAttribute("lagrangianResultDto", lagrangianResultDto);
        req.setAttribute("cubicResultDto", cubicResultDto);
        req.setAttribute("numberOfGaps", numberOfGaps + 2);

        req.getRequestDispatcher("WEB-INF/jsp/result.jsp").include(req, resp);
    }
}
