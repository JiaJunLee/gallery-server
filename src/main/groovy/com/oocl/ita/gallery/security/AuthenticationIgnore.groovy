package com.oocl.ita.gallery.security


import java.lang.annotation.*

@Target([ ElementType.METHOD ])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface AuthenticationIgnore {

}