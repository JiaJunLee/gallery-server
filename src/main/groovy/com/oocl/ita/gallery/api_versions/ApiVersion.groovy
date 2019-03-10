package com.oocl.ita.gallery.api_versions

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target;
/**
 *
 * ApiVersion
 *
 * @date 3/10/2019 3:11 PM
 * @version 1.0
 *
 */
@Target([ElementType.METHOD, ElementType.TYPE])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@interface ApiVersion {

    String value() default ''

}
