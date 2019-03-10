package com.oocl.ita.gallery.common.util

import org.springframework.web.multipart.MultipartFile
import sun.misc.BASE64Encoder

/**
 *
 * Created by Justin Liu on 3/10/2019.
 *
 */
class Base64Util {

    static String getImageBase64(MultipartFile file) throws Exception {
        String base64 = null
        try {
            File f = File.createTempFile("tmp", null)
            file.transferTo(f)
            FileInputStream inputFile = new FileInputStream(f)
            byte[] buffer = new byte[(int) f.length()]
            inputFile.read(buffer)
            inputFile.close()
            base64 = new BASE64Encoder().encode(buffer).replaceAll("[\\s*\t\n\r]", "")
            f.deleteOnExit()
        } catch (IOException e) {
            e.printStackTrace()
        }
        return base64
    }
}
