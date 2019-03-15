package com.oocl.ita.gallery.image

import com.oocl.ita.gallery.common.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service;
/**
 *
 * ImageService
 *
 * @date 3/10/2019 11:21 AM
 * @version 1.0
 *
 */
@Service
class ImageService extends BaseService<Image, String> {

    @Autowired ImageRepository imageRepository

    @Override
    PagingAndSortingRepository<Image, String> getRepository() {
        return this.imageRepository
    }

    Page<Image> findAllByTagsLike(List<String> tags, Pageable pageable) {
        return this.imageRepository.findAllByTagsLike(tags, pageable)
    }

}
