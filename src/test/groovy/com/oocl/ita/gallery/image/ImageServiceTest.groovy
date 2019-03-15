package com.oocl.ita.gallery.image

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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

    def 'should return image page size 2 when findAllByTagsLike given tags'() {
        given:
        List<String> tags = ['a']
        Pageable pageable = new PageRequest(0, 30)
        imageRepository.findAllByTagsLike(tags, pageable) >> new PageImpl<Image>([new Image(), new Image()])

        when:
        Page<Image> likes = imageService.findAllByTagsLike(tags, pageable)

        then:
        likes != null
        likes.size() == 2
    }
}
