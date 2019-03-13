package com.oocl.ita.gallery.file

import com.oocl.ita.gallery.api_versions.ApiVersion
import com.oocl.ita.gallery.api_versions.ApiVersions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 *
 * Created by Justin Liu on 3/10/2019.
 *
 */
@Controller
@RequestMapping('/files')
@CrossOrigin(value = "http://localhost:8080", allowCredentials = "true")
class FileController {

    @Autowired
    FileService fileService

    @GetMapping('/{file_id}')
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageFile> get(@PathVariable('file_id') String fileId) {
        ImageFile imageFile = fileService.findById(fileId)
        if (imageFile == null) {
            return new ResponseEntity<ImageFile>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<ImageFile>(imageFile, HttpStatus.OK)
    }

    @GetMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<List<ImageFile>> getAll() {
        return new ResponseEntity<List<ImageFile>>(fileService.findAll().toList(), HttpStatus.OK)
    }

    @PostMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageFile> save(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<ImageFile>(fileService.saveFile(null, file), HttpStatus.CREATED)
    }

    @PutMapping
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageFile> update(@RequestParam("fileId") String fileId, @RequestParam("file") MultipartFile file) {
        if (!fileService.isExists(fileId)) {
            return new ResponseEntity<ImageFile>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<ImageFile>(fileService.saveFile(fileId, file), HttpStatus.RESET_CONTENT)
    }

    @DeleteMapping('/{file_id}')
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<ImageFile> delete(@PathVariable('file_id') String fileId) {
        ImageFile imageFile = fileService.findById(fileId)
        if (imageFile == null) {
            return new ResponseEntity<ImageFile>(HttpStatus.NOT_FOUND)
        }
        fileService.deleteById(fileId)
        return new ResponseEntity<ImageFile>(HttpStatus.NO_CONTENT)
    }
}
