package com.oocl.ita.gallery.user

import org.junit.Assert
import org.junit.Test

class UserTest {

    @Test
    void should_return_true_when_isAdministrator_given_user_Administrator() {
        //Given
        User user = new User(type: User.UserType.ADMINISTRATOR)

        //When
        boolean result = user.isAdministrator()

        //Then
        Assert.assertTrue(result)
    }

    @Test
    void should_return_true_when_Public_given_user_Public() {
        //Given
        User user = new User(type: User.UserType.PUBLIC)

        //When
        boolean result = user.isPublic()

        //Then
        Assert.assertTrue(result)
    }
}
