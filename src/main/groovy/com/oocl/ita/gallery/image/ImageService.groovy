package com.oocl.ita.gallery.image

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
/**
 *
 * ImageService
 *
 * @author Burgess Li
 * @date 3/10/2019 11:21 AM
 * @version 1.0
 *
 */
@Service
class ImageService {

    @Autowired ImageRepository imageRepository

    Image save(Image image) {
        return this.imageRepository.save(image)
    }

}
