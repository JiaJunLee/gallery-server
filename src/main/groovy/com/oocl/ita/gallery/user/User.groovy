package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.BaseDocument
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document('user')
class User extends BaseDocument{

    static final enum UserType {
        ADMINISTRATOR,
        PUBLIC
    }

    @Field('username')
    String username

    @Field('hs_key')
    String hsKey

    @Field('password')
    String password

    @Field('name')
    String nickname

    @Field('gender')
    Integer gender = 0

    @Field('type')
    UserType type

    boolean isAdministrator() {
        return this.type == UserType.ADMINISTRATOR
    }

    boolean isPublic() {
        return this.type == UserType.PUBLIC
    }

}
