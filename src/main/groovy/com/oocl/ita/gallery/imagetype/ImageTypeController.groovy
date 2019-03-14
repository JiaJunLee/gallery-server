package com.oocl.ita.gallery.imagetype

import com.oocl.ita.gallery.api_versions.ApiVersion
import com.oocl.ita.gallery.api_versions.ApiVersions
import com.oocl.ita.gallery.security.AuthenticationIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/image-types')
class ImageTypeController {

    @Autowired ImageTypeService imageTypeService

    @PostMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageType> save(@RequestBody ImageType imageType){
        return new ResponseEntity<ImageType>(imageTypeService.save(imageType), HttpStatus.CREATED)
    }

    @DeleteMapping('/{delete_Id}')
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageType> deleteById(@PathVariable('delete_Id') String imageId){
        ImageType imageType = imageTypeService.findById(imageId)
        if(imageType == null){
            return new ResponseEntity<ImageType>(HttpStatus.NOT_FOUND)
        }
        imageTypeService.deleteById(imageId)
        return new ResponseEntity<ImageType>(HttpStatus.NO_CONTENT)
    }

    @GetMapping('/{image_id}')
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageType> get(@PathVariable('image_id') String imageId) {
        ImageType imageType = imageTypeService.findById(imageId)
        if (imageType == null) {
            return new ResponseEntity<ImageType>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<ImageType>(imageType, HttpStatus.OK)
    }

    @GetMapping
    @ApiVersion(ApiVersions.VERSION_1)
    @AuthenticationIgnore
    ResponseEntity<List<ImageType>> getAll() {
        return new ResponseEntity<List<ImageType>>(imageTypeService.findAll().toList(), HttpStatus.OK)
    }

    @PutMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageType> update(@RequestBody ImageType imageType) {
        if (!imageTypeService.isExists(imageType?.id)) {
            return new ResponseEntity<ImageType>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<ImageType>(imageTypeService.save(imageType), HttpStatus.RESET_CONTENT)
    }

}
