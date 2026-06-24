package com.example.notification_hub.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class ApiKeyAndRateLimitInterceptor implements HandlerInterceptor {

    // Humari secure API Key (Real world mein yeh application.properties se aati hai)
    private static final String SECURE_API_KEY = "abhavya_secret_key_123";

    // Balti (Bucket) banayi: Max 3 requests, aur har 1 minute mein 3 naye tokens add honge
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(1))))
            .build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. SECURITY CHECK: Request ke Headers mein se "X-API-KEY" nikalo
        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !apiKey.equals(SECURE_API_KEY)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 Unauthorized
            response.getWriter().write("{\"error\": \"API key is missing! or is Wrong!\"}");
            response.setContentType("application/json");
            return false; // Request ko aage Controller tak MAT jaane do
        }

        // 2. RATE LIMIT CHECK: Kya balti mein token bacha hai?
        if (!bucket.tryConsume(1)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429 Too Many Requests
            response.getWriter().write("{\"error\": \"Rate Limit Exceeded!\"}");
            response.setContentType("application/json");
            return false; // Request block
        }

        return true; // Agar dono check pass ho gaye, toh Controller ko request de do
    }
}