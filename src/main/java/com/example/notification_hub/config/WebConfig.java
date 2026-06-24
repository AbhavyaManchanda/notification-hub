package com.example.notification_hub.config;

import com.example.notification_hub.interceptor.ApiKeyAndRateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration // Tells Spring: "Yeh configuration file hai, ise dhyan se padho"
    public class WebConfig implements WebMvcConfigurer {

        private final ApiKeyAndRateLimitInterceptor interceptor;

        // DI se humne apna interceptor yahan bulaya
        public WebConfig(ApiKeyAndRateLimitInterceptor interceptor) {
            this.interceptor = interceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // Humne bola: Hamara guard sirf "/api/notify" waale raste par khada karo
            registry.addInterceptor(interceptor).addPathPatterns("/api/notify");
        }
    }

