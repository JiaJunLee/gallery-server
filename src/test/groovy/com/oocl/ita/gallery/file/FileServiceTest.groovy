package groovy.com.oocl.ita.gallery.file

import com.oocl.ita.gallery.file.FileRepository
import com.oocl.ita.gallery.file.FileService
import com.oocl.ita.gallery.file.ImageFile
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

/**
 *
 * Created by Justin Liu on 3/11/2019.
 *
 */
class FileServiceTest extends Specification {

    FileService fileService
    FileRepository fileRepository

    def setup() {
        fileRepository = Mock(FileRepository)
        fileService = new FileService(
                fileRepository: fileRepository
        )
    }

    def 'should return not null when getRepository'() {
        when:
        PagingAndSortingRepository repository = fileService.getRepository()

        then:
        repository != null
    }

    def 'should return imageFile not null when saveFile'() {
        given:
        fileService = Stub(FileService) {
            save(new ImageFile()) >> new ImageFile()
        }
        MultipartFile file = Mock(MultipartFile)

        when:
        ImageFile imageFile = fileService.saveFile("123456", file)

        then:
        imageFile != null
    }

    def 'should return imageFile when ConstructImageFile given MultipartFile file'() {
        given:
        byte[] bytes = [200000]
        MultipartFile file = Stub(MultipartFile) {
            getOriginalFilename() >> "abc.jpg"
            getContentType() >> "img"
            getSize() >> Long.valueOf("1000")
            getBytes() >> bytes
        }

        when:
        ImageFile imageFile = fileService.constructImageFile("123456", file)

        then:
        imageFile != null
        imageFile.getId() == "123456"
        imageFile.getFileSize() == "1000"
    }


}
