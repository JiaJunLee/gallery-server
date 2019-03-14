package com.oocl.ita.gallery.imagetype

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageTypeRepository extends PagingAndSortingRepository<ImageType, String>{

    ImageType findByTypeName(String typeName);

}