package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
import java.lang.reflect.Method

@Aspect
@Configuration
class AuthenticationAop {

    @Autowired JWT jwt
    @Autowired UserService userService

    @Around('execution(* com.oocl.ita.gallery.*.*Controller.*(..))')
    Object checkUserType(ProceedingJoinPoint joinPoint) {
        Method method = joinPoint.getSignature().getDeclaringType().getDeclaredMethods().find { it.name == joinPoint.getSignature().name }
        if (method?.isAnnotationPresent(AuthenticationAnnotation)) {
            AuthenticationAnnotation authenticationAnnotation = method.getAnnotation(AuthenticationAnnotation)
            RequestAttributes ra = RequestContextHolder.getRequestAttributes()
            ServletRequestAttributes sra = (ServletRequestAttributes) ra
            HttpServletRequest request = sra.getRequest()
            String jwtToken = request?.getCookies()?.find { it?.name == 'token' }?.value
            User user = userService.findByUsername(jwt.getUsername(jwtToken))
            if (!jwtToken || user?.type != authenticationAnnotation.type()) {
                throw new AuthenticationException(ErrorMsgConstants.AUTH_NO_ACCESS)
            }
        }
        return joinPoint.proceed()
    }

}