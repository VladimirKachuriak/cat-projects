package org.example.Filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.Filter;
import java.io.IOException;



public class EncodingFilter implements Filter {
    private static final Logger log = Logger.getLogger(EncodingFilter.class.getSimpleName());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.debug("init EncodingFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("do EncodingFilter: UTF8");
        servletRequest.setCharacterEncoding("UTF8");
        servletResponse.setCharacterEncoding("UTF8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("destroy EncodingFilter");
    }
}
