package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.UserService
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.reflect.Method

@Aspect
@Configuration
class AuthenticationAop extends BasicHttpAuthenticationFilter {

    @Autowired
    JWT jwt
    @Autowired
    UserService userService

    @Around('execution(* com.oocl.ita.gallery.*.*Controller.*(..))')
    Object checkUserType(ProceedingJoinPoint joinPoint) {
        Method method = joinPoint.getSignature().getDeclaringType().getDeclaredMethods().find { it.name == joinPoint.getSignature().name }
        if (!method?.isAnnotationPresent(AuthenticationIgnore)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse()
            getSubject(request, response).login(new Token(token: request.getCookies().find { it?.name == 'token' }?.value))
        }
        return joinPoint.proceed()
    }

}