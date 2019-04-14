package com.jeanvar.housingfinance.config;

import com.jeanvar.housingfinance.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Bean
    @Profile("test")
    AuthInterceptor testAuthInterceptor() {
        return new AuthInterceptor() {
            @Override
            public boolean preHandle(
                HttpServletRequest request,
                HttpServletResponse response,
                Object handler
            ) throws Exception {
                return true;
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns("/api/v1/user/**");
    }
}
