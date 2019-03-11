package groovy.com.oocl.ita.gallery.file

import com.oocl.ita.gallery.file.FileRepository
import com.oocl.ita.gallery.file.FileService
import com.oocl.ita.gallery.file.ImageFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

/**
 *
 * Created by Justin Liu on 3/11/2019.
 *
 */
class FileServiceTest extends Specification {

    static FileService fileService
    static FileRepository fileRepository
    MultipartFile file

    def setup() {
        fileRepository = Mock(FileRepository)
        file = Mock()
    }

    def 'should return not null when getRepository'() {
        given:
        fileService = new FileService(
                fileRepository: fileRepository
        )

        expect:
        fileService.getRepository() != null
    }

    def 'should return imageFile not null when saveFile'() {
        given:
        ImageFile input = new ImageFile()
        ImageFile output = new ImageFile()
        fileService = Stub(FileService) {
            save(input) >> output
        }

        when:
        ImageFile imageFile = fileService.saveFile("123456", file)

        then:
        imageFile != null
    }

    def 'should return imageFile when ConstructImageFile given MultipartFile file'() {
        given:
        byte[] bytes = [200000]
        file = Stub(MultipartFile) {
            getOriginalFilename() >> "abc.jpg"
            getContentType() >> "img"
            getSize() >> Long.valueOf("1000")
            getBytes() >> bytes
        }

        when:
        ImageFile imageFile = fileService.ConstructImageFile("123456", file)

        then:
        imageFile != null
        imageFile.getId() == "123456"
        imageFile.getFileSize() == "1000"
    }


}
