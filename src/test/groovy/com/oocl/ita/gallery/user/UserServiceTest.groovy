package com.oocl.ita.gallery.user

import io.jsonwebtoken.lang.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService

    @Test
    void should_return_user_when_findAllByType_given_user_type_administrator() {
        Assert.notNull(userService.findAllByType(User.UserType.ADMINISTRATOR))
    }
}
