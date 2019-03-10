package com.oocl.ita.gallery.file

import com.oocl.ita.gallery.common.BaseDocument
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

/**
 *
 * Created by Justin Liu on 3/10/2019.
 *
 */
@Document('files')
class ImageFile extends BaseDocument {

    @Field('file_name')
    String fileName

    @Field('file_type')
    String fileType

    @Field('file_size')
    String fileSize

    @Field('file_content')
    String fileContent
}
