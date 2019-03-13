package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.BaseDocument
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document('user')
class User extends BaseDocument{

     static final class UserType {
         static final String ADMINISTRATOR = "ADMINISTRATOR"
         static final String PUBLIC = "PUBLIC"
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
    String type

    boolean isAdministrator() {
        return this.type == UserType.ADMINISTRATOR.type
    }

    boolean isPublic() {
        return this.type == UserType.PUBLIC.type
    }

}
