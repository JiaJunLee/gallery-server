package com.oocl.ita.gallery.image

import com.oocl.ita.gallery.api_versions.ApiVersion
import com.oocl.ita.gallery.api_versions.ApiVersions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * ImageController
 *
 * @date 3/10/2019 11:21 AM
 * @version 1.0
 *
 */
@RestController
@RequestMapping('/images')
class ImageController {

    @Autowired ImageService imageService

    @GetMapping('/{image_id}')
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<Image> get(@PathVariable('image_id') String imageId) {
        Image image = imageService.findById(imageId)
        if (image == null) {
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<Image>(image, HttpStatus.OK)
    }

    @GetMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<List<Image>> getAll() {
        return new ResponseEntity<List<Image>>(imageService.findAll().toList(), HttpStatus.OK)
    }

    @PostMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<Image> save(@RequestBody Image image) {
        return new ResponseEntity<Image>(imageService.save(image), HttpStatus.CREATED)
    }

    @PutMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<Image> update(@RequestBody Image image) {
        if (!imageService.isExists(image?.id)) {
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<Image>(imageService.save(image), HttpStatus.RESET_CONTENT)
    }

    @DeleteMapping('/{image_id}')
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<Image> delete(@PathVariable('image_id') String imageId) {
        Image image = imageService.findById(imageId)
        if (image == null) {
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND)
        }
        imageService.deleteById(imageId)
        return new ResponseEntity<Image>(HttpStatus.NO_CONTENT)
    }

}
