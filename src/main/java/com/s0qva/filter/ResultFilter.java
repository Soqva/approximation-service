package com.s0qva.filter;

import com.s0qva.validator.ApproximationParameter;
import com.s0qva.validator.ApproximationParametersValidator;
import com.s0qva.validator.Validator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(value = "/result", servletNames = {"ResultServlet"})
public class ResultFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Validator validator = new ApproximationParametersValidator();

        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String leftBorder = servletRequest.getParameter("leftBorder");
        String rightBorder = servletRequest.getParameter("rightBorder");
        String numberOfGaps = servletRequest.getParameter("numberOfGaps");
        String interpolationPoint = servletRequest.getParameter("interpolationPoint");
        String function = servletRequest.getParameter("function");

        if (!validator.isValid(ApproximationParameter.NUMBER, leftBorder, rightBorder, numberOfGaps, interpolationPoint)
                || !validator.isValid(ApproximationParameter.STRING, function)) {
            servletRequest.setAttribute("invalidField", "Some fields are invalid");
            servletRequest.getRequestDispatcher("index.jsp").forward(servletRequest, servletResponse);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
