package com.oocl.ita.gallery.user

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByUsername(String username)

    List<User> findAllByType(User.UserType type)

}
