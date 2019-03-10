package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.security.utils.HMAC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/user')
class UserController {

    @Autowired
    UserService userService

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
        return new ResponseEntity(userService.createUser(userDB), HttpStatus.CREATED);
    }
}