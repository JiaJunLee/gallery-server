package com.oocl.ita.gallery.security

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter extends BasicHttpAuthenticationFilter {


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return true
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response)
        } catch (Exception e) {
            ((HttpServletResponse) response).sendRedirect("/user/denied")
            return false
        }
    }

}