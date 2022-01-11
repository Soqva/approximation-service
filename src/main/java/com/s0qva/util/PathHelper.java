package com.s0qva.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathHelper {
    private final String JSP_PATH_FORMAT = "/WEB-INF/jsp/%s.jsp";

    public String getJspPath(String jspName) {
        return String.format(JSP_PATH_FORMAT, jspName);
    }
}
