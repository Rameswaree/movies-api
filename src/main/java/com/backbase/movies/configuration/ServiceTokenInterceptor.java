package com.backbase.movies.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom service token interceptor for endpoint security
 */
@Component
@Setter
@Getter
public class ServiceTokenInterceptor implements HandlerInterceptor {

    private static final String SERVICE_TOKEN_HEADER = "Service-Token";
    @Value("${service-token}")
    private String serviceToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String serviceToken = request.getHeader(SERVICE_TOKEN_HEADER);

        if (serviceToken == null || !serviceToken.equals(this.serviceToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Missing or invalid Service-Token");
            return false;
        }
        return true;
    }
}
