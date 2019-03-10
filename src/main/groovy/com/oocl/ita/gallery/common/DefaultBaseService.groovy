package com.oocl.ita.gallery.common

import org.springframework.data.repository.PagingAndSortingRepository;
/**
 *
 * DefaultBaseService
 *
 * @date 3/10/2019 7:37 PM
 * @version 1.0
 *
 */
class DefaultBaseService extends BaseService {

    PagingAndSortingRepository pagingAndSortingRepository

    @Override
    PagingAndSortingRepository getRepository() {
        return pagingAndSortingRepository
    }

}
