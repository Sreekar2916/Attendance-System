package com.company.attendance.config;

import com.company.attendance.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> registerFilter() {

        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtFilter);
        bean.addUrlPatterns("/api/attendance/*"); // protect APIs

        return bean;
    }
}