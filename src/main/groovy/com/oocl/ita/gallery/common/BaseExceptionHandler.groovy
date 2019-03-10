package com.oocl.ita.gallery.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * BaseExceptionHandler
 *
 * @date 3/10/2019 12:45 PM
 * @version 1.0
 *
 */
@ControllerAdvice
class BaseExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity handleException(Exception e) {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

}