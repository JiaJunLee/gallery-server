package com.oocl.ita.gallery.file

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 *
 * Created by Justin Liu on 3/10/2019.
 *
 */
@Repository
interface FileRepository extends PagingAndSortingRepository<ImageFile, String> {

}