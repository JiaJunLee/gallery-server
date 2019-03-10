package com.oocl.ita.gallery.image

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
/**
 *
 * ImageController
 *
 * @date 3/10/2019 11:21 AM
 * @version 1.0
 *
 */
@Controller
@RequestMapping('/images')
class ImageController {

    @Autowired ImageService imageService

    @PostMapping
    ResponseEntity<Image> save(@RequestBody Image image) {
        return new ResponseEntity<Image>(imageService.save(image), HttpStatus.OK)
    }

}
