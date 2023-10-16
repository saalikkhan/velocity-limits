package com.vault.velocity.limits.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Slf4j
public class LoggingIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestId = UUID.randomUUID().toString();
//        request.setAttribute("request_id", requestId);
//        log.info("Entering Request Id - {}, Request Path - {}, Http Method - {}, Timestamp - {}",
//                requestId, request.getRequestURI(), request.getMethod(), LocalDateTime.now());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//        response.setHeader("requestId", request.getAttribute("request_id").toString());
        log.info("Exiting Request Id - {}, Request Path - {}, Http Method - {}, Timestamp - {}",
                request.getAttribute("request_id"), request.getRequestURI(), request.getMethod(), LocalDateTime.now());
    }

}
