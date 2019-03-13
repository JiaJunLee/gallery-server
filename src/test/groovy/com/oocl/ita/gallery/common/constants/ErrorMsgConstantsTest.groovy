package com.oocl.ita.gallery.common.constants

import spock.lang.Specification

class ErrorMsgConstantsTest extends Specification {


    def 'should return #result when get msg: #msg'() {
        given:
        ErrorMsgConstants constants = new ErrorMsgConstants()

        expect:
        msg == result

        where:
        msg                                          | result
        ErrorMsgConstants.REG_FAIL_CONFLICT_USERNAME | "username has been registered"
        ErrorMsgConstants.AUTH_NO_ACCESS             | "User cannot access"
        ErrorMsgConstants.AUTH_NOT_FOUND_USER        | "User cannot found"
        ErrorMsgConstants.AUTH_FAIL                  | "Authentication Failed"
        ErrorMsgConstants.AUTH_TOKEN_UNRECOGNIZED    | "Token cannot be parse"
        ErrorMsgConstants.AUTH_DENIED                | "Access denied"
    }
}
