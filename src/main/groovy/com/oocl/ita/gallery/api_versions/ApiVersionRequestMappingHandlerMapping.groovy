package com.oocl.ita.gallery.api_versions

import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

import java.lang.reflect.Method
/**
 *
 * ApiVersionRequestMappingHandlerMapping
 *
 * @date 3/10/2019 3:36 PM
 * @version 1.0
 *
 */
class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if(mappingInfo != null){
            RequestMappingInfo apiVersionMappingInfo = getApiVersionMappingInfo(method, handlerType)
            return apiVersionMappingInfo == null ? mappingInfo : apiVersionMappingInfo.combine(mappingInfo)
        }
        return mappingInfo
    }

    private static RequestMappingInfo getApiVersionMappingInfo(Method method, Class<?> handlerType) {
        ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class)
        return apiVersion?.value() ? RequestMappingInfo
                .paths(apiVersion.value())
                .build() : null
    }

}
