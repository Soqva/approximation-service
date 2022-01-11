package com.s0qva.filter;

import com.s0qva.validator.ApproximationParameterType;
import com.s0qva.validator.ExistenceOfApproximationParameterValidator;
import com.s0qva.validator.Validator;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(value = "/result", servletNames = {"ResultServlet"})
public class ResultFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Validator validator = new ExistenceOfApproximationParameterValidator();

        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String leftBorder = servletRequest.getParameter("leftBorder");
        String rightBorder = servletRequest.getParameter("rightBorder");
        String numberOfGaps = servletRequest.getParameter("numberOfGaps");
        String interpolationPoint = servletRequest.getParameter("interpolationPoint");
        String function = servletRequest.getParameter("function");

        if (validator.isValid(ApproximationParameterType.NUMBER, leftBorder, rightBorder, numberOfGaps, interpolationPoint)
                && validator.isValid(ApproximationParameterType.STRING, function)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/approximation_service?invalidField=true");
        }
    }
}
