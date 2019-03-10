package com.oocl.ita.gallery.user

import com.oocl.ita.gallery.common.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service

@Service
class UserService extends BaseService<User, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    PagingAndSortingRepository<User, String> getRepository() {
        return this.userRepository
    }

    List<User> findAllByType(User.UserType type) {
        return userRepository.findAllByType(type)
    }

    User findByUsername(String username) {
        return userRepository.findByUsername(username)
    }

    User createUser(User user) {
        if (userRepository.findByUsername(user.username) == null) {
            this.save(user)
        } else {
            throw new RuntimeException()
        }
        return user
    }


}
