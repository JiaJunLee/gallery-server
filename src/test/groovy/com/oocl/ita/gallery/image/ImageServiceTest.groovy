package com.oocl.ita.gallery.image

import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

class ImageServiceTest extends Specification{

    static ImageService imageService

    void setup() {
        imageService = new ImageService(imageRepository:  Mock(ImageRepository.class))
    }

    def 'should return imageRepository when getRepository'() {
        when:
        PagingAndSortingRepository<Image, String> repository = imageService.getRepository()

        then:
        repository != null
    }
}
