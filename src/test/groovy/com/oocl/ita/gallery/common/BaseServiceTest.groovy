package groovy.com.oocl.ita.gallery.common

import com.oocl.ita.gallery.GalleryApplication
import com.oocl.ita.gallery.common.BaseService
import com.oocl.ita.gallery.common.DefaultBaseService
import com.oocl.ita.gallery.image.Image
import com.oocl.ita.gallery.image.ImageRepository
import com.oocl.ita.gallery.image.ImageService
import com.sun.xml.internal.bind.v2.model.core.ID
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification
import spock.lang.Unroll;

/**
 *
 * BaseServiceTest
 *
 * @author Burgess Li
 * @date 3/10/2019 6:42 PM
 * @version 1.0
 *
 */
@SpringBootTest(classes = GalleryApplication)
class BaseServiceTest<T, ID> extends Specification {

    static BaseService baseService

    def setupSpec() {
        baseService = new DefaultBaseService(
                pagingAndSortingRepository: Stub(PagingAndSortingRepository.class) {
                    findById('ID') >> new Optional(new Object())
                }
        )
    }

    @Unroll
    def 'should return #result when exists database record id: #id'() {
        when:
        boolean exists = baseService.isExists(id)

        then:
        exists == result

        where:
        id                  |   result
        'ID'                |   true
        'ID_NOT_EXISTS'     |   false
    }

//    def 'should return object with ID when save object'() {
//        given:
//        Object object = new Object()
//
//        when:
//        Object resultObject = getBaseService().save(object)
//
//        then:
//        resultObject != null
//        resultObject.getAt('id') != null
//    }

}
