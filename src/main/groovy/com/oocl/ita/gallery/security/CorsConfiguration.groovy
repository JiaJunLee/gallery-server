package com.oocl.ita.gallery.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration implements WebMvcConfigurer {

    @Value('${website.baseurl}')
    private String websiteBaseURL

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(websiteBaseURL)
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowCredentials(true).maxAge(3600)
    }

}
