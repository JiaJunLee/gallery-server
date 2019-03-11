package com.oocl.ita.gallery.user.exception

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import org.junit.Assert
import org.junit.Test

class DuplicatedUserExceptionTest {

    @Test
    public void should_return_String_not_null_when_toString_given_duplicatedUserException_string_errorConstants() {
        //Given
        Exception e = new DuplicatedUserException(ErrorMsgConstants.REG_FAIL_CONFLICT_USERNAME)

        //When
        String msg = e.toString()

        //Then
        Assert.assertNotNull(msg)
        Assert.assertEquals(ErrorMsgConstants.REG_FAIL_CONFLICT_USERNAME, msg)
    }
}
