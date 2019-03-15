package com.oocl.ita.gallery.image

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class ImageControllerTest extends Specification {
    ImageController imageController
    ImageService imageService

    def setup() {
        imageService = Mock(ImageService)
        imageController = new ImageController(imageService: imageService)
    }


    def 'should return success when get given image id'() {
        given:
        String id = 1
        Image image = new Image(id: id)
        imageService.findById(id) >> image

        when:
        ResponseEntity<Image> response = imageController.get(id)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().getId() == id
    }

    def 'should return success when get given not exist image id'() {
        given:
        String id = 1
        imageService.findById(id) >> null

        when:
        ResponseEntity<Image> response = imageController.get(id)

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def 'should return pages when get given pageIndex and pageSize'() {
        given:
        List<Image> images = [new Image()]
        Pageable pageable = new PageRequest(0, 30)
        imageService.findAll(pageable) >> new PageImpl<Image>(images)

        when:
        ResponseEntity<Map> response = imageController.get(0, 30, "")

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().get("images").size() == 1
    }

    def 'should return pages when get given pageIndex and pageSize and tags'() {
        given:
        List<Image> images = [new Image(tags: ['tag'])]
        Pageable pageable = new PageRequest(0, 30)
        imageService.findAllByTagsLike(['tag'], pageable) >> new PageImpl<Image>(images)

        when:
        ResponseEntity<Map> response = imageController.get(0, 30, "tag")

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().get("images").size() == 1
    }

    def 'should return success when save given image'() {
        given:
        String id = 1
        Image image = new Image(id: id)
        imageService.save(image) >> image

        when:
        ResponseEntity<Image> response = imageController.save(image)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody().getId() == id
    }

    def 'should return success when update given image'() {
        given:
        String id = 1
        Image image = new Image(id: id)
        imageService.save(image) >> image
        imageService.isExists(image?.id) >> true

        when:
        ResponseEntity<Image> response = imageController.update(image)

        then:
        response.getStatusCode() == HttpStatus.RESET_CONTENT
    }

    def 'should return success when update given not exist image'() {
        given:
        String id = 1
        Image image = new Image(id: id)
        imageService.save(image) >> image
        imageService.isExists(2) >> true

        when:
        ResponseEntity<Image> response = imageController.update(image)

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def 'should return success when delete given exist image id'() {
        given:
        String id = 1
        Image image = new Image(id: id)
        imageService.deleteById(id) >> true
        imageService.findById(id) >> image
        imageService.isExists(1 as String) >> true

        when:
        ResponseEntity<Image> response = imageController.delete(id)

        then:
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def 'should return success when delete given not exist image id'() {
        given:
        String id = 1
        imageService.findById(id) >> null
        imageService.deleteById(id) >> false

        when:
        ResponseEntity<Image> response = imageController.delete(id)

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

}
