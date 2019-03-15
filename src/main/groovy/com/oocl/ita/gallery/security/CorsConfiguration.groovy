package com.oocl.ita.gallery.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration implements WebMvcConfigurer {

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://zha-ita021-w10:8889")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowCredentials(true).maxAge(3600)
    }

}