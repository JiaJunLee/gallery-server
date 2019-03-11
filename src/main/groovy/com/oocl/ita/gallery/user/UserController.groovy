package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.common.constants.MsgConstants
import com.oocl.ita.gallery.security.AuthenticationException
import com.oocl.ita.gallery.security.utils.HMAC
import com.oocl.ita.gallery.security.utils.JWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping('/user')
class UserController {

    @Autowired
    JWT jwt

    @Autowired
    UserService userService

    @RequestMapping('/denied')
    ResponseEntity denied() {
        return new ResponseEntity<String>(ErrorMsgConstants.AUTH_DENIED, HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/register")
    ResponseEntity register(@RequestBody User user) {
        String hsKey = HMAC.generateKey(HMAC.HMAC_SHA512)
        User userDB = new User(
                username: user.username,
                nickname: user.nickname,
                gender: user.gender,
                hsKey: hsKey,
                password: HMAC.digest(user.password, hsKey, HMAC.HMAC_SHA512),
                type: User.UserType.PUBLIC
        )
        return new ResponseEntity<User>(userService.createUser(userDB), HttpStatus.CREATED)
    }

    @PostMapping("/login")
    ResponseEntity login(@RequestBody User user, HttpServletResponse httpServletResponse) {
        User userDB = userService.findByUsername(user.username)
        if (userDB == null) {
            throw new AuthenticationException(ErrorMsgConstants.AUTH_NOT_FOUND_USER)
        }

        if (!HMAC.validate(user.password, userDB?.hsKey, HMAC.HMAC_SHA512, userDB?.password)) {
            throw new AuthenticationException(ErrorMsgConstants.AUTH_FAIL)
        }

        String jwtToken = jwt?.sign(['id': userDB?.id, 'username': userDB?.username, 'hsKey': userDB?.hsKey, 'hsPassword': userDB?.password])

        Cookie cookie = new Cookie('token', jwtToken)
        cookie.setHttpOnly(true)
        cookie.setPath('/')
        cookie.setMaxAge(JWT.TOKEN_EXPIRE_TIME)
        httpServletResponse.addCookie(cookie)

        return new ResponseEntity<String>(MsgConstants.USER_LOGIN, HttpStatus.OK)
    }
}