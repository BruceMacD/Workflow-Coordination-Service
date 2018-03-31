package com.cc.workflow.security;

import com.cc.workflow.services.AuthService;
import com.cc.workflow.trace.LoggingTraceRepository;

import io.jsonwebtoken.JwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;

/**
 * Needs to be > Ordered.LOWEST_PRECEDENCE - 10 in order for unauthorized requests
 * to be passed to the @{LoggingTraceRepository}
 */

@WebFilter("/*")
@Component(value = "JWTFilter")
@Order(value = Ordered.LOWEST_PRECEDENCE - 9)
public class JWTFilter implements Filter {
    private static final HashSet<String> excludedPaths = new HashSet<String>() {{
        add("/register");
        add("/auth");
    }};

    @Autowired
    private AuthService authService;

    @Autowired
    private LoggingTraceRepository traceRepository;

    @Value("${jwt.auth.header}")
    String authHeader;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String authHeaderVal = httpRequest.getHeader(authHeader);

        if (!isExcluded(httpRequest)) {
            if (null == authHeaderVal) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            try {
                JWTUser jwtUser = authService.getUser(authHeaderVal);
                httpRequest.setAttribute("jwtUser", jwtUser);
            } catch (JwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return;
            }
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private boolean isExcluded(HttpServletRequest request) {
        String uri = request.getRequestURI();

        for (String end : excludedPaths) {
            if (uri.endsWith(end))
                return true;
        }
        return false;
    }
}