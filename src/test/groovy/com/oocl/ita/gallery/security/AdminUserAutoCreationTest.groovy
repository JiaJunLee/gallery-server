package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import spock.lang.Specification

class AdminUserAutoCreationTest extends Specification{

    AdminUserAutoCreation autoCreation
    UserService userService

    def setup() {
        userService = Mock(UserService)
        autoCreation = new AdminUserAutoCreation(userService: userService)
    }


    def 'should create user when run'() {
        given:
        userService.createUser(_) >> new User()

        when:
        autoCreation.run("run")

        then:
        1*userService.createUser(_)
    }


}
