package com.oocl.ita.gallery.image

import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

class ImageServiceTest extends Specification{

    static ImageService imageService
    ImageRepository imageRepository

    void setup() {
        imageRepository = Mock(ImageRepository.class)
        imageService = new ImageService(imageRepository:  imageRepository)
    }

    def 'should return imageRepository when getRepository'() {
        when:
        PagingAndSortingRepository<Image, String> repository = imageService.getRepository()

        then:
        repository != null
    }

    def 'should return image list size 2 when findAllByTagsLike given tags'() {
        given:
        List<String> tags = ['a']
        imageRepository.findAllByTagsLike(tags) >> [new Image(),new Image()]

        when:
        List<Image> likes = imageService.findAllByTagsLike(tags)

        then:
        likes != null
        likes.size() == 2
    }
}
