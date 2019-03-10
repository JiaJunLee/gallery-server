package com.oocl.ita.gallery.common

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

/**
 *
 * BaseDocument
 *
 * @date 3/10/2019 12:41 PM
 * @version 1.0
 *
 */
class BaseDocument {

    @Id
    String id

    @CreatedDate
    @JsonFormat(pattern = 'yyyy/MM/dd HH:mm:ss')
    Date createdDate = new Date()

    @LastModifiedDate
    @JsonFormat(pattern = 'yyyy/MM/dd HH:mm:ss')
    Date lastModifiedDate

}
