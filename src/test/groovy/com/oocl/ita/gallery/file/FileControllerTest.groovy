package com.oocl.ita.gallery.file

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification
import spock.lang.Unroll

class FileControlleTest extends Specification {

    FileController fileController
    FileService fileService


    def setup() {
        fileService = Mock(FileService)
        fileController = new FileController(fileService: fileService)
    }

    @Unroll
    def 'should return #result when get id: #id'() {
        given:
        fileService.findById('ID') >> new ImageFile(id: 'ID')
        fileService.findById('ID_NULL') >> null

        expect:
        fileController.get(id).getStatusCode() == statusCode
        fileController.get(id).getStatusCodeValue() == statusCodeValue

        where:
        id        | statusCode           | statusCodeValue
        'ID'      | HttpStatus.OK        | 200
        'ID_NULL' | HttpStatus.NOT_FOUND | 404
    }


    def 'should return #result when getAll'() {
        given:
        fileService.findAll() >> new ArrayList<ImageFile>()

        when:
        ResponseEntity<List<ImageFile>> responseEntity = fileController.getAll()

        then:
        responseEntity.getStatusCodeValue() == 200
        responseEntity.getStatusCode() == HttpStatus.OK
    }


    def 'should return #result when save'() {
        given:
        fileService.saveFile(null, _) >> new ImageFile(id: 'ID', fileName: 'fileName')
        MultipartFile file = Mock(MultipartFile)

        when:
        ResponseEntity<ImageFile> responseEntity = fileController.save(file)

        then:
        responseEntity.getStatusCodeValue() == 201
        responseEntity.getStatusCode() == HttpStatus.CREATED
        responseEntity.getBody().getFileName() == 'fileName'
    }


    def 'should return #result when update'() {
        given:
        fileService.isExists('fileId_NULL') >> false
        fileService.isExists('fileId') >> true
        fileService.saveFile('fileId', _) >> new ImageFile(id: 'ID', fileName: 'fileName')
        MultipartFile file = Mock(MultipartFile)

        expect:
        fileController.update(fileId, file).getStatusCode() == statusCode
        fileController.update(fileId, file).getStatusCodeValue() == statusCodeValue
        fileController.update(fileId, file).getBody()?.getFileName() == fileName

        where:
        fileId        | statusCode               | statusCodeValue | fileName
        'fileId_NULL' | HttpStatus.NOT_FOUND     | 404             | null
        'fileId'      | HttpStatus.RESET_CONTENT | 205             | 'fileName'
    }

    def 'should return #result when delete'() {
        given:
        fileService.findById('ID') >> new ImageFile(id: 'ID', fileName: 'fileName')
        fileService.findById('ID_NULL') >> null
        fileService.deleteById(_) >> null

        expect:
        fileController.delete(fileId).getStatusCode() == statusCode
        fileController.delete(fileId).getStatusCodeValue() == statusCodeValue

        where:
        fileId    | statusCode            | statusCodeValue
        'ID_NULL' | HttpStatus.NOT_FOUND  | 404
        'ID'      | HttpStatus.NO_CONTENT | 204
    }


}
