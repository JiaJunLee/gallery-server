package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.BaseService
import com.oocl.ita.gallery.user.exception.DuplicatedUserException
import org.assertj.core.util.Lists
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.powermock.api.support.membermodification.MemberMatcher

import static org.powermock.api.support.membermodification.MemberModifier.suppress

class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserService();

    @Mock
    private UserRepository userRepository;


    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(userRepository.findAllByType(Mockito.eq(User.UserType.PUBLIC))).thenReturn(Lists.newArrayList())
        Mockito.when(userRepository.findByUsername(Mockito.eq("b"))).thenReturn(new User())
        Mockito.when(userRepository.findByUsername(Mockito.eq("a"))).thenReturn(null)
    }

    @Test
    void should_return_userRepository_when_getRepository() {
        //When
        UserRepository userRepository1 = userService.getRepository();

        //Then
        Assert.assertNotNull(userRepository1)
    }


    @Test
    void should_return_empty_user_list_when_findAllByType_given_user_type_public() {
        //When
        List<User> userList = userService.findAllByType(User.UserType.PUBLIC)

        //Then
        Assert.assertEquals(0, userList.size())
    }

    @Test
    void should_return_user_null_when_findByUsername_given_user_name_a() {
        //Given
        String username = "a"

        //When
        User user = userService.findByUsername(username)

        //Then
        Assert.assertNull(user)
    }

    @Test
    void should_return_user_not_null_when_findByUsername_given_user_name_b() {
        //Given
        String username = "b"

        //When
        User user = userService.findByUsername(username)

        //Then
        Assert.assertNotNull(user)
    }

    @Test
    public void should_return_user_not_null_when_createuser_given_user_name_a() {
        //Given
        suppress(MemberMatcher.methodsDeclaredIn(BaseService.class))
        User user = new User(username: "a", nickname: "A", gender: 1, hsKey: "key",
                password: "pwd", type: User.UserType.PUBLIC)

        //When
        User userDB = userService.createUser(user)

        //Then
        Assert.assertNotNull(userDB)
    }

    @Test(expected = DuplicatedUserException.class)
    public void should_catch_DuplicatedUserException_when_create_given_user_name_b() {
        //Given
        suppress(MemberMatcher.methodsDeclaredIn(BaseService.class))
        User user = new User(username: "b", nickname: "B", gender: 1, hsKey: "key",
                password: "pwd", type: User.UserType.PUBLIC)

        //When
        User userDB = userService.createUser(user)
    }
}
