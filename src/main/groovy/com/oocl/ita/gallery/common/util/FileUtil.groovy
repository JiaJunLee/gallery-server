package com.oocl.ita.gallery.common.util

/**
 *
 * Created by Justin Liu on 3/10/2019.
 *
 */
class FileUtil {

    static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath)
        if (!targetFile.exists()) {
            targetFile.mkdirs()
        }
        FileOutputStream out = new FileOutputStream(filePath + System.currentTimeMillis().toString() + fileName);
        out.write(file)
        out.flush()
        out.close()
    }
}
