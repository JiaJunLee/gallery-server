package com.oocl.ita.gallery.security

import org.junit.Assert
import org.junit.Test

class TokenTest {

    @Test
    void should_return_token_string_when_getPrincipal() {
        //Given
        String tokenStr = "token"
        Token token = new Token()
        token.setToken(tokenStr)

        //When
        String principal = token.getPrincipal()

        //Then
        Assert.assertNotNull(principal)
        Assert.assertEquals(tokenStr,principal)
    }

    @Test
    void should_return_token_string_when_getCredentials() {
        //Given
        String tokenStr = "token"
        Token token = new Token()
        token.setToken(tokenStr)

        //When
        String credentials = token.getCredentials()

        //Then
        Assert.assertNotNull(credentials)
        Assert.assertEquals(tokenStr,credentials)
    }
}
