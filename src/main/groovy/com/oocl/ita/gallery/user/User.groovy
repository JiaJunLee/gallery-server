package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.BaseDocument
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document('user')
class User extends BaseDocument{

    @Field('username')
    String username

    @Field('password')
    String password

    @Field('name')
    String nickname

    @Field('gender')
    Integer gender = 0

    @Field('type')
    Integer type = 0
}
