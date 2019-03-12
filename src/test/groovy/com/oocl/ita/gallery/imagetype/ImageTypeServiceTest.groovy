package groovy.com.oocl.ita.gallery.imagetype

import com.oocl.ita.gallery.imagetype.ImageTypeRepository
import com.oocl.ita.gallery.imagetype.ImageTypeService
import spock.lang.Specification


class ImageTypeServiceTest extends Specification{

    static ImageTypeService imageTypeService
    static ImageTypeRepository imageTypeRepository

    def setup(){
        imageTypeRepository = Mock(ImageTypeRepository)
    }

    def 'should return not null when getRepository'() {
        given:
        imageTypeService = new ImageTypeService(
                imageTypeRepository: imageTypeRepository
        )

        expect:
        imageTypeService.getRepository() != null
    }

}
