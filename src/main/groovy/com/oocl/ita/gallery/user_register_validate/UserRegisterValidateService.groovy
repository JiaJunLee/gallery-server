package com.oocl.ita.gallery.user_register_validate

import com.oocl.ita.gallery.common.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service

@Service
class UserRegisterValidateService extends BaseService<UserRegisterValidate, String> {

    @Autowired
    UserRegisterValidateRepository userRegisterValidateRepository
    
    @Override
    PagingAndSortingRepository<UserRegisterValidate, String> getRepository() {
        return userRegisterValidateRepository
    }

}
