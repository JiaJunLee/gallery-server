package com.oocl.ita.gallery.image

import com.oocl.ita.gallery.api_versions.ApiVersion
import com.oocl.ita.gallery.api_versions.ApiVersions
import com.oocl.ita.gallery.file.FileService
import com.oocl.ita.gallery.file.ImageFile
import com.oocl.ita.gallery.imagetype.ImageTypeService
import com.oocl.ita.gallery.security.AuthenticationIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.*

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

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

    @Autowired
    ImageService imageService

    @Autowired
    FileService fileService

    @Autowired
    ImageTypeService imageTypeService

    @GetMapping('/{image_id}')
    @ApiVersion(ApiVersions.VERSION_1)
    @AuthenticationIgnore
    ResponseEntity<Image> get(@PathVariable('image_id') String imageId) {
        Image image = imageService.findById(imageId)
        if (image == null) {
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<Image>(image, HttpStatus.OK)
    }

    @GetMapping
    @ApiVersion(ApiVersions.VERSION_1)
    @AuthenticationIgnore
    ResponseEntity<Map> get(
            @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "30") int pageSize, String tags) {
        Pageable pageable = new PageRequest(pageIndex, pageSize)
        Page<Image> imagePage = null
        if (tags) {
            imagePage = imageService.findAllByTagsLike(tags.split(' ').toList(), pageable)
        } else {
            imagePage = imageService.findAll(pageable)
        }
        return new ResponseEntity<Map>([
                images          : imagePage?.getContent(),
                total           : imagePage?.getTotalElements(),
                currentPageIndex: imagePage?.getNumber()
        ], HttpStatus.OK)
    }

    @PostMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<Image> save(@RequestBody Image image) {
        if (image?.imageFile?.id) {
            ImageFile imageFile = fileService.findById(image.imageFile.id)
            BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(new ByteArrayInputStream(Base64Utils.decodeFromString(imageFile?.fileContent))))
            image.imageWidth = bufferedImage.getWidth()
            image.imageHeight = bufferedImage.getHeight()
        }
        if (image?.imageType?.typeName) {
            image.imageType = imageTypeService.findByTypeName(image?.imageType?.typeName)
        }
        return new ResponseEntity<Image>(imageService.save(image), HttpStatus.CREATED)
    }

    @PutMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<Image> update(@RequestBody Image image) {
        if (!imageService.isExists(image?.id)) {
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND)
        }
        if (image?.imageType?.typeName) {
            image.imageType = imageTypeService.findByTypeName(image?.imageType?.typeName)
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
