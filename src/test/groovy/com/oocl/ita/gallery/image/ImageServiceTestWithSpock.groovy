package groovy.com.oocl.ita.gallery.image

import com.oocl.ita.gallery.GalleryApplication
import com.oocl.ita.gallery.image.Image
import com.oocl.ita.gallery.image.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
/**
 *
 * ImageServiceTestWithSpock
 *
 * @date 3/10/2019 4:56 PM
 * @version 1.0
 *
 */
@SpringBootTest(classes = GalleryApplication)
class ImageServiceTestWithSpock extends Specification {

    @Autowired ImageService imageService

    def 'should return not null when get repository'() {
        expect:
        imageService.getRepository() != null
    }

    def 'should return image when save image'() {
        given:
        Image image = new Image(
                tags: ['TAG1', 'TAG2', 'TAG3'],
                viewCount: 0,
                imageWidth: 1920,
                imageHeight: 1080
        )

        when:
        Image resultImage = imageService.save(image)

        then:
        resultImage != null
        resultImage.id != null
    }

    def 'should return images when find all images'() {
        when:
        List<Image> images = imageService.findAll().toList()

        then:
        images != null
    }

    def 'should return image when find image by id'() {
        given:
        Image image = imageService.save(new Image(
                tags: ['TAG1', 'TAG2', 'TAG3'],
                viewCount: 0,
                imageWidth: 1920,
                imageHeight: 1080
        ))

        when:
        Image imageResult = imageService.findById(image.id)

        then:
        imageResult != null
    }

}
