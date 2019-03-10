package com.oocl.ita.gallery.image

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository;
/**
 *
 * ImageRepository
 *
 * @date 3/10/2019 11:20 AM
 * @version 1.0
 *
 */
@Repository
interface ImageRepository extends PagingAndSortingRepository<Image, String> {

}
