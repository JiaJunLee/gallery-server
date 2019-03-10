package com.oocl.ita.gallery.api_versions

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
/**
 *
 * ApiVersionConfiguration
 *
 * @date 3/10/2019 3:41 PM
 * @version 1.0
 *
 */
@Configuration
@ConditionalOnWebApplication
class ApiVersionConfiguration {

    @Bean
    WebMvcRegistrations apiVersionRegistrations(){
        return new WebMvcRegistrations() {
            @Override
            RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new ApiVersionRequestMappingHandlerMapping()
            }

            @Override
            RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
                return null
            }

            @Override
            ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
                return null
            }
        }
    }

}
