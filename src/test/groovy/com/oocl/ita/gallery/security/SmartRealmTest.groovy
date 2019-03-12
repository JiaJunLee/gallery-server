package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.UserService
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authz.AuthorizationInfo
import org.junit.*
import org.junit.rules.ExpectedException
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SmartRealmTest {

    @InjectMocks
    private SmartRealm smartRealm = new SmartRealm()

    @Mock
    private JWT jwt

    @Mock
    private UserService userService

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
        //Mockito.when(jwt.getUsername(Mockito.eq("abc"))).thenReturn(null)
    }


    @Test
    void should_return_name_when_getName() {
        //When
        String name = smartRealm.getName()

        //Then
        Assert.assertNotNull(name)
        Assert.assertEquals("SMART-REALM",name)
    }

    @Test
    void should_return_true_when_supports_given_AuthenticationToken() {
        //Given
        Token token = new Token()

        //When
        boolean supports = smartRealm.supports(token)

        //Then
        Assert.assertTrue(supports)
    }

    @Test
    void should_return_false_when_supports_given_AuthenticationToken() {
        //Given
        AuthenticationToken token = new UsernamePasswordToken()

        //When
        boolean supports = smartRealm.supports(token)

        //Then
        Assert.assertFalse(supports)
    }

    @Test
    void should_return_null_when_doGetAuthorizationInfo() {
        //When
        AuthorizationInfo info = smartRealm.doGetAuthorizationInfo(null)

        //Then
        Assert.assertNull(info)
    }

    @Ignore
    void should_throw_token_AUTH_TOKEN_UNRECOGNIZED_when_doGetAuthenticationInfo_given_token_abc() {
        //When
        String tokenStr = "abc"
        Token token = new Token(token: tokenStr)

        //Then
        thrown.expect(AuthenticationException.class)
        thrown.expectMessage(ErrorMsgConstants.AUTH_TOKEN_UNRECOGNIZED)

        //When
        smartRealm.doGetAuthenticationInfo(token)

    }
}
