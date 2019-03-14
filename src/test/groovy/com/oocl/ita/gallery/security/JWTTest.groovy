package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.User
import spock.lang.Specification

class JWTTest extends Specification {

    JWT jwt
    User user
    String initToken

    void setup() {
        jwt = new JWT()
        user = new User(username: "Tom")
        initToken = jwt.sign([username:user.username])
    }

    def "should return same token  when sign the same username"() {
        given:
        Map checkClaims = [username: user.username]

        when:
        String token = jwt.sign(checkClaims)

        then:
        token == initToken
    }


}
