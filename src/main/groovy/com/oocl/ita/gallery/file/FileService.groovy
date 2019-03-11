package com.oocl.ita.gallery.file

import com.oocl.ita.gallery.common.BaseService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import org.springframework.web.multipart.MultipartFile

/**
 *
 * Created by Justin Liu on 3/10/2019.
 *
 */
@Service
@Slf4j
class FileService extends BaseService<ImageFile, String> {

    @Autowired
    FileRepository fileRepository

    @Override
    PagingAndSortingRepository<ImageFile, String> getRepository() {
        return fileRepository
    }

    ImageFile saveFile(String fileId, MultipartFile file) {
        return save(ConstructImageFile(fileId, file))
    }

    private ImageFile ConstructImageFile(String fileId, MultipartFile file) {
        new ImageFile(
                id: fileId,
                fileName: System.currentTimeMillis().toString() + file.getOriginalFilename(),
                fileType: file.getContentType(),
                fileSize: file.getSize(),
                fileContent: Base64Utils.encodeToString(file.getBytes())
        )
    }

}
