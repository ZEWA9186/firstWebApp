package org.example.testsecurity.config;

import org.example.testsecurity.annotation.ApiPrefix;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                "/api", c -> c.isAnnotationPresent(ApiPrefix.class)
        );
    }
}
