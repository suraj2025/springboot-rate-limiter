package com.suraj.rate_limiter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.suraj.rate_limiter.service.RateLimiterService;

import java.io.IOException;

@Component
public class RateLimitFilter implements Filter {
    private final RateLimiterService rateLimiterService;

    public RateLimitFilter(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();

        boolean allowed = rateLimiterService.allowRequest(clientIp);

        if (allowed) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Rate limit exceeded!");
            return;
        }
    }
}
