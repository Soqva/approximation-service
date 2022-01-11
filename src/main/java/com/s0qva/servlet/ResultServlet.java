package com.s0qva.servlet;

import com.s0qva.dto.ApproximationResultDto;
import com.s0qva.service.ApproximationService;
import com.s0qva.service.CubicSplineService;
import com.s0qva.service.LagrangianApproximationService;
import com.s0qva.service.ApproximatedFunction;
import com.s0qva.util.PathHelper;
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

        ApproximatedFunction approximatedFunction = new ApproximatedFunction(function);
        lagrangianApproximationService = new LagrangianApproximationService(approximatedFunction, leftBorder, rightBorder, numberOfGaps);
        cubicSplineService = new CubicSplineService(approximatedFunction, leftBorder, rightBorder, numberOfGaps);

        ApproximationResultDto lagrangianResultDto = lagrangianApproximationService.calculateValueInterpolationPoint(interpolationPoint);
        ApproximationResultDto cubicResultDto = cubicSplineService.calculateValueInterpolationPoint(interpolationPoint);

        req.setAttribute("lagrangianResultDto", lagrangianResultDto);
        req.setAttribute("cubicResultDto", cubicResultDto);
        req.setAttribute("numberOfPoints", numberOfGaps + 2);

        req.getRequestDispatcher(PathHelper.getJspPath("result")).forward(req, resp);
    }
}
