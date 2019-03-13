package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authz.AuthorizationInfo
import spock.lang.Specification

class SmartRealmTest extends Specification {

    SmartRealm smartRealm
    UserService userService
    JWT jwt


    def setup() {
        userService = Mock(UserService)
        jwt = Mock(JWT)
        smartRealm = new SmartRealm(userService: userService, jwt: jwt)
    }


    def 'should return AUTH_TOKEN_UNRECOGNIZED when doGetAuthenticationInfo given no username token'() {
        given:
        jwt.getUsername(_) >> ""
        Token token = new Token(token: "")

        when:
        smartRealm.doGetAuthenticationInfo(token)

        then:
        def ex = thrown(Exception)
        ex.getMessage() == ErrorMsgConstants.AUTH_TOKEN_UNRECOGNIZED
    }

    def 'should return AUTH_NOT_FOUND_USER when doGetAuthenticationInfo given username no in DB'() {
        given:
        jwt.getUsername(_) >> "a"
        userService.findByUsername(_) >> null
        Token token = new Token(token: "")

        when:
        smartRealm.doGetAuthenticationInfo(token)

        then:
        def ex = thrown(Exception)
        ex.getMessage() == ErrorMsgConstants.AUTH_NOT_FOUND_USER
    }

    def 'should return AUTH_FAIL when doGetAuthenticationInfo given token is incorrect'() {
        given:
        jwt.getUsername(_) >> "a"
        jwt.verify(_, _) >> false
        userService.findByUsername(_) >> new User()
        Token token = new Token(token: "")

        when:
        smartRealm.doGetAuthenticationInfo(token)

        then:
        def ex = thrown(Exception)
        ex.getMessage() == ErrorMsgConstants.AUTH_FAIL
    }

    def 'should return SimpleAuthenticationInfo when doGetAuthenticationInfo given token is correct'() {
        given:
        jwt.getUsername(_) >> "a"
        jwt.verify(_, _) >> true
        userService.findByUsername(_) >> new User()
        Token token = new Token(token: "")

        when:
        AuthenticationInfo info = smartRealm.doGetAuthenticationInfo(token)

        then:
        info != null
    }

    def 'should return SMART-REALM when getName'() {
        when:
        String name = smartRealm.getName()

        then:
        name != null
        name == "SMART-REALM"
    }

    def 'should return null when doGetAuthorizationInfo'() {
        when:
        AuthorizationInfo info = smartRealm.doGetAuthorizationInfo()

        then:
        info == null
    }

    def 'should return false when supports given UsernamePasswordToken'() {
        given:
        AuthenticationToken token = new UsernamePasswordToken()

        when:
        boolean result = smartRealm.supports(token)

        then:
        result == false
    }

    def 'should return #result when supports given #token'() {

        expect:
        smartRealm.supports(token) == result

        where:
        token                       | result
        new Token()                 | true
    }
}