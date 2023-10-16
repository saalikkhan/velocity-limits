package com.vault.velocity.limits.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("request_id", requestId);
        log.info("Entering Request Id - {}, Request Path - {}, Http Method - {}, Timestamp - {}",
                requestId, request.getRequestURI(), request.getMethod(), LocalDateTime.now());

        response.setHeader("requestId", request.getAttribute("request_id").toString());
        filterChain.doFilter(request, response);
    }
}
