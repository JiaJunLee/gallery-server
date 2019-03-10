package com.oocl.ita.gallery.image

import com.oocl.ita.gallery.common.BaseDocument
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

/**
 *
 * Image
 *
 * @date 3/10/2019 11:20 AM
 * @version 1.0
 *
 */
@Document('images')
class Image extends BaseDocument {

    @Field('tags')
    List<String> tags = []

    @Field('view_count')
    Integer viewCount = 0

    @Field('image_width')
    Integer imageWidth = 0

    @Field('image_height')
    Integer imageHeight = 0

}
