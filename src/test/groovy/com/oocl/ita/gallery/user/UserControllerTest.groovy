package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.HMAC
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class UserControllerTest extends Specification {

    UserController userController
    UserService userService


    def setup() {
        userService = Mock(UserService)
        userController = new UserController(userService: userService)
    }


    def 'should return response with with code 401 id when denied'() {
        when:
        ResponseEntity responseEntity = (ResponseEntity) userController.denied()

        then:
        responseEntity != null
        responseEntity.statusCode == HttpStatus.UNAUTHORIZED
    }


    def 'should return response with with code 201 when register'() {
        given:
        String nickName = "nickName"
        String username = "user"
        userService.createUser(_) >> new User(username: username)
        User user = new User(username: username,
                nickname: nickName,
                gender: 1,
                password: "123"
        )

        when:
        ResponseEntity responseEntity = (ResponseEntity) userController.register(user)
        println responseEntity.body

        then:
        responseEntity != null
        responseEntity.statusCode == HttpStatus.CREATED
        ((User) responseEntity.body).username == username
    }


    def 'should throw AuthenticationException when login given username not exist in DB'() {
        given:
        String usernameNotInDB = "username not exist"
        userService.findByUsername(usernameNotInDB) >> null

        when:
        userController.login(new User(username: usernameNotInDB), new MockHttpServletResponse())

        then:
        def ex = thrown(Exception)
        ex.toString() == ErrorMsgConstants.AUTH_NOT_FOUND_USER

    }

    def 'should throw AuthenticationException when login given error password'() {
        given:
        String username = "username"
        String passwordCorrect = "123"
        String passwordError = "1234"
        userService.findByUsername(username) >> new User(username: username, password: passwordCorrect)

        when:
        userController.login(new User(username: username, password: passwordError), new MockHttpServletResponse())

        then:
        def ex = thrown(Exception)
        ex.toString() == ErrorMsgConstants.AUTH_FAIL
    }

    def 'should return login Login Success when login given correct password'() {
        given:
        String username = "username"
        String passwordCorrect = "123"
        String hsKey = HMAC.generateKey(HMAC.HMAC_SHA512)
        userService.findByUsername(username) >> new User(username: username, password: HMAC.digest(passwordCorrect, hsKey, HMAC.HMAC_SHA512), hsKey: hsKey)

        when:
        ResponseEntity<String> response = userController.login(new User(username: username, password: passwordCorrect), new MockHttpServletResponse())

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
    }


}
