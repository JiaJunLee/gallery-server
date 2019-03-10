package com.oocl.ita.gallery.imagetype

import com.oocl.ita.gallery.common.BaseDocument
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document('imageTypes')
class ImageType extends BaseDocument{

    @Field('type_name')
    String typeName

    @Field('description')
    String description

}
