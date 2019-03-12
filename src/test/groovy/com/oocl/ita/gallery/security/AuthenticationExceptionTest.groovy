package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import org.junit.Assert
import org.junit.Test

class AuthenticationExceptionTest {

    @Test
    public void should_return_String_not_null_when_AuthenticationException_toString_given__string_errorConstants() {
        //Given
        Exception e = new AuthenticationException(ErrorMsgConstants.AUTH_FAIL)

        //When
        String msg = e.toString()

        //Then
        Assert.assertNotNull(msg)
        Assert.assertEquals(ErrorMsgConstants.AUTH_FAIL, msg)
    }
}
