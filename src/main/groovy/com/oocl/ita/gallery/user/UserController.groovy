package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.HMAC
import com.oocl.ita.gallery.user.vo.RegisterRequestVo
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
    ResponseEntity register(@RequestBody RegisterRequestVo registerVo) {
        ResponseEntity<String> response
        String hsKey = HMAC.generateKey(HMAC.HMAC_SHA512)
        if (registerVo.password == registerVo.passwordAck) {
            User user = new User(
                    username: registerVo.username,
                    nickname: registerVo.nickname,
                    gender: registerVo.gender,
                    hsKey: hsKey,
                    password:  HMAC.digest(registerVo.password, hsKey, HMAC.HMAC_SHA512),
                    type: User.UserType.PUBLIC
            )
            response = userService.createUser(user) != null ?
                    new ResponseEntity("", HttpStatus.CREATED) : new ResponseEntity(ErrorMsgConstants.REG_FAIL_CONFLICT_USERNAME, HttpStatus.OK)
        } else {
            response = new ResponseEntity(ErrorMsgConstants.REG_FAIL_DIFFERENT_PASSWORD, HttpStatus.OK)
        }
        return response
    }
}
