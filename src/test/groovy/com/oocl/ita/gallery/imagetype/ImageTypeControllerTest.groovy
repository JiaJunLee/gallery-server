package com.oocl.ita.gallery.imagetype

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class ImageTypeControllerTest extends Specification{
    ImageTypeController imageTypeController
    ImageTypeService imageTypeService

    def setup() {
        imageTypeService = Mock(ImageTypeService)
        imageTypeController = new ImageTypeController(imageTypeService: imageTypeService)
    }


    def 'should return success when get given imageType id'() {
        given:
        String id = 1
        ImageType imageType = new ImageType(id: id)
        imageTypeService.findById(id) >> imageType

        when:
        ResponseEntity<ImageType> response = imageTypeController.get(id)

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().getId() == id
    }

    def 'should return success when get given not exist imageType id'() {
        given:
        String id = 1
        imageTypeService.findById(id) >> null

        when:
        ResponseEntity<ImageType> response = imageTypeController.get(id)

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def 'should return success when get all given imageType id'() {
        given:
        List<ImageType> imageTypes = [new ImageType()]
        imageTypeService.findAll() >> imageTypes

        when:
        ResponseEntity<List<ImageType>> response = imageTypeController.getAll()

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 1
    }

    def 'should return success when save given imageType'() {
        given:
        String id = 1
        ImageType imageType = new ImageType(id: id)
        imageTypeService.save(imageType) >> imageType

        when:
        ResponseEntity<ImageType> response = imageTypeController.save(imageType)

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody().getId() == id
    }

    def 'should return success when update given imageType'() {
        given:
        String id = 1
        ImageType imageType = new ImageType(id: id)
        imageTypeService.save(imageType) >> imageType
        imageTypeService.isExists(imageType?.id) >> true

        when:
        ResponseEntity<ImageType> response = imageTypeController.update(imageType)

        then:
        response.getStatusCode() == HttpStatus.RESET_CONTENT
    }

    def 'should return success when update given not exist imageType'() {
        given:
        String id = 1
        ImageType imageType = new ImageType(id: id)
        imageTypeService.save(imageType) >> imageType
        imageTypeService.isExists(2) >> true

        when:
        ResponseEntity<ImageType> response = imageTypeController.update(imageType)

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def 'should return success when delete given exist imageType id'() {
        given:
        String id = 1
        ImageType imageType = new ImageType(id: id)
        imageTypeService.deleteById(id) >> true
        imageTypeService.findById(id) >> imageType
        imageTypeService.isExists(1 as String) >> true

        when:
        ResponseEntity<ImageType> response = imageTypeController.deleteById(id)

        then:
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def 'should return success when delete given not exist imageType id'() {
        given:
        String id = 1
        imageTypeService.findById(id) >> null
        imageTypeService.deleteById(id) >> false

        when:
        ResponseEntity<ImageType> response = imageTypeController.deleteById(id)

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
