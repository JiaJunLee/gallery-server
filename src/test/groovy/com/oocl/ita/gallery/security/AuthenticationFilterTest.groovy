package com.oocl.ita.gallery.security

import org.apache.shiro.web.subject.support.WebDelegatingSubject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.modules.junit4.PowerMockRunner
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

import javax.servlet.http.HttpServletRequest

@RunWith(PowerMockRunner.class)
class AuthenticationFilterTest {

    @Test
    void should_return_false_when_isAccessAllowed_given_executeLogin_throw_exception() {
        //Given
        AuthenticationFilter filter = new AuthenticationFilter()

        //When
        boolean result = filter.isAccessAllowed(null, new MockHttpServletResponse(), null)

        //Then
        Assert.assertFalse(result)
    }

    @Test
    void should_return_true_and_execute_login_when_executeLogin_given_request_with_need_filter_path() {
        //Given
        AuthenticationFilter filter = PowerMockito.spy(new AuthenticationFilter())
        HttpServletRequest request = new MockHttpServletRequest()
        request.servletPath = "/user/login2"
        WebDelegatingSubject webDelegatingSubject = PowerMockito.mock(WebDelegatingSubject.class)
        PowerMockito.doReturn(webDelegatingSubject).when(filter, "getSubject", Matchers.any(), Matchers.any())

        //When
        boolean result = filter.executeLogin(request, new MockHttpServletResponse())

        //Assert
        Assert.assertTrue(result)
        Mockito.verify(webDelegatingSubject, Mockito.times(1)).login(Matchers.any())
    }

    @Test
    void should_return_true_when_executeLogin_given_no_need_filter_path() {
        //Given
        AuthenticationFilter filter = new AuthenticationFilter()
        HttpServletRequest request = new MockHttpServletRequest()
        request.servletPath = "/user/login"

        //When
        boolean result = filter.executeLogin(request, new MockHttpServletResponse())

        //Then
        Assert.assertTrue(result)
    }
}
