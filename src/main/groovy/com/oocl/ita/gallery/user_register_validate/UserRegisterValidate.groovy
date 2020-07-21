package com.oocl.ita.gallery.user_register_validate

import com.oocl.ita.gallery.common.BaseDocument
import com.oocl.ita.gallery.user.User
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Field;

class UserRegisterValidate extends BaseDocument {

    @DBRef
    @Field('user_ref')
    User registerUser

    @Field('expiration_time')
    long expirationTime

}
