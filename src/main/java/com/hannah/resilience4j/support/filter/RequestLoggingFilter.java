package com.hannah.resilience4j.support.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String uuid = UUID.randomUUID().toString();
        try {
            MDC.put("request_id", uuid);

            log.info("Request - Method: {} | URL: {} | Client IP: {} | User Agent: {} | Time: {}",
                    httpRequest.getMethod(),
                    httpRequest.getRequestURL(),
                    httpRequest.getRemoteAddr(),
                    httpRequest.getHeader("User-Agent"),
                    LocalDateTime.now()
            );

            filterChain.doFilter(servletRequest, servletResponse);

            log.info("Response Status: {}", httpResponse.getStatus());
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


}

