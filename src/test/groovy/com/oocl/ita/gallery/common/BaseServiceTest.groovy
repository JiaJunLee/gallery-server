package groovy.com.oocl.ita.gallery.common

import com.oocl.ita.gallery.GalleryApplication
import com.oocl.ita.gallery.common.BaseDocument
import com.oocl.ita.gallery.common.BaseService
import com.oocl.ita.gallery.common.DefaultBaseService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification
import spock.lang.Unroll

/**
 *
 * BaseServiceTest
 *
 * @author Burgess Li
 * @date 3/10/2019 6:42 PM
 * @version 1.0
 *
 */
class BaseServiceTest extends Specification {

    static BaseService baseService
    static Map<String, Object> inputMap
    static Map<String, Object> outputMap

    def setupSpec() {
        inputMap = [
                'FIND': new BaseDocument(id: 'ID'),
                'SAVE': new BaseDocument(),
                'SAVE_ALL': [new BaseDocument(), new BaseDocument()]
        ]
        outputMap = [
                'FIND': new BaseDocument(id: 'ID'),
                'SAVE': new BaseDocument(id: 'NEW_ID'),
                'SAVE_ALL': [new BaseDocument(id: 'ID_1'), new BaseDocument(id: 'ID_2')]
        ]
        baseService = new DefaultBaseService(
                pagingAndSortingRepository: Stub(PagingAndSortingRepository.class) {
                    findById(inputMap['FIND'].id) >> new Optional(outputMap['FIND'])
                    save(inputMap['SAVE']) >> outputMap['SAVE']
                    saveAll(inputMap['SAVE_ALL']) >> outputMap['SAVE_ALL']
                }
        )
    }

    @Unroll
    def 'should return #isExists when exists database record id: #id'() {
        expect:
        isExists == baseService.isExists(id)

        where:
        id                  |   isExists
        'ID'                |   true
        'ID_NOT_EXISTS'     |   false
    }

    @Unroll
    def 'should return document when find by id: #id'() {
        expect:
        result == (BaseDocument) baseService.findById(id)

        where:
        id                  |   result
        'ID'                |   outputMap['FIND']
        'ID_NOT_EXISTS'     |   null
    }

    def 'should return document with id when save document'() {
        given:
        BaseDocument document = inputMap['SAVE']

        when:
        BaseDocument resultDocument = (BaseDocument) baseService.save(document)

        then:
        resultDocument != null
        resultDocument.id != null
    }

    def 'should return all documents with id when save all documents'() {
        given:
        List documents = inputMap['SAVE_ALL']

        when:
        List resultDocuments = baseService.saveAll(documents)

        then:
        resultDocuments != null
        resultDocuments.every { BaseDocument document -> document.id != null }
    }

    def 'should return true when delete document'() {
        expect:
        baseService.deleteById('ID')
    }

    def 'should return true when delete all documents'() {
        expect:
        baseService.deleteAll(['ID_1', 'ID_2'])
    }

}
