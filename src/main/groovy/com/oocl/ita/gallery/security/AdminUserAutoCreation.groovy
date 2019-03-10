package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.security.utils.HMAC
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AdminUserAutoCreation implements CommandLineRunner {

    private static final String username = 'admin'
    private static final String password = '123456'

    @Autowired UserService userService

    @Override
    void run(String... args) throws Exception {
        List<User> users = userService.findAllByType(User.UserType.ADMINISTRATOR)
        if (users == null || users.isEmpty()) {
            String hsKey = HMAC.generateKey(HMAC.HMAC_SHA512)
            User user = new User(
                    username: username,
                    hsKey: hsKey,
                    password: HMAC.digest(password, hsKey, HMAC.HMAC_SHA512),
                    type: User.UserType.ADMINISTRATOR,
                    gender: 0
            )
            userService.createUser(user)
        }
    }

}