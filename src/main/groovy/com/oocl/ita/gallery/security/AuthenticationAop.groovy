package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import org.apache.shiro.SecurityUtils
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestAttributes
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

    private static final List<String> IGNORE_PATH = [
            '/user/register',
            '/user/login',
            '/user/denied',
            '/v1/images'
    ]

    @Around('execution(* com.oocl.ita.gallery.*.*Controller.*(..))')
    Object checkUserType(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse()
        if (!IGNORE_PATH.contains(((HttpServletRequest)request).getServletPath())) {
            getSubject(request, response).login(new Token(token: request.getCookies().find { it?.name == 'token' }?.value))
        }
        return joinPoint.proceed()
    }

}