package com.oocl.ita.gallery.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class BaseExceptionHandlerTest extends Specification{



    def 'should return ResponseEntity with httpstatus 500 when handleException given exception'() {
        given:
        BaseExceptionHandler handler = new BaseExceptionHandler()
        Exception e = new Exception();
        e.initCause(new Throwable())

        when:
        ResponseEntity responseEntity = handler.handleException(e)


        then:
        responseEntity != null
        responseEntity.statusCode == HttpStatus.INTERNAL_SERVER_ERROR

    }

}
