package com.oocl.ita.gallery.imagetype

import com.oocl.ita.gallery.common.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service


@Service
class ImageTypeService extends BaseService<ImageType, String>{

    @Autowired ImageTypeRepository imageTypeRepository

    @Override
    PagingAndSortingRepository<ImageType, String> getRepository() {
        return this.imageTypeRepository
    }
}
