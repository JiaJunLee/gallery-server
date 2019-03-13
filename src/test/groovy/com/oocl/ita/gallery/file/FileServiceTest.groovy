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

    FileService fileService
    FileRepository fileRepository

    def setup() {
        fileRepository = Mock(FileRepository)
        fileService = new FileService(
                fileRepository: fileRepository
        )
    }

    def 'should return not null when getRepository'() {
        expect:
        fileService.getRepository() != null
    }

    def 'should return imageFile not null when saveFile'() {
        given:
        fileService = Stub(FileService) {
            save(new ImageFile()) >> new ImageFile()
        }
        MultipartFile file = Mock(MultipartFile)

        expect:
        fileService.saveFile("123456", file) != null
    }

    def 'should return imageFile when ConstructImageFile given MultipartFile file'() {
        given:
        byte[] bytes = [200000]
        String filename = "abc.jpg"
        String size = "1000"
        String fileId = "123456"
        String type = "image"
        MultipartFile file = Stub(MultipartFile) {
            getOriginalFilename() >> filename
            getContentType() >> type
            getSize() >> Long.valueOf(size)
            getBytes() >> bytes
        }

        when:
        ImageFile imageFile = fileService.constructImageFile(fileId, file)

        then:
        imageFile != null
        imageFile.getId() == fileId
        imageFile.getFileSize() == size
    }


}
