package com.oocl.ita.gallery.common.constants

import spock.lang.Specification

class MsgConstantsTest extends Specification {


    def 'should return #result when get msg: #msg'() {
        given:
        MsgConstants constants = new MsgConstants()

        expect:
        msg == result

        where:
        msg                     | result
        MsgConstants.USER_LOGIN | "Login Success"

    }
}
