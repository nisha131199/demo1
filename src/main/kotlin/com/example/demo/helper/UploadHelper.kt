package com.example.demo.helper

import org.springframework.core.io.ClassPathResource
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class UploadHelper(str: String) {
    //private val url = "C:\\Users\\hp\\IdeaProjects\\demo1\\src\\main\\resources\\static\\${str}"    //resources/static
    private val url = ClassPathResource("static/${str}").file.absolutePath   //target

    fun save(file: MultipartFile): Boolean{
        try {
            /*
            //read
            val inStream = file.inputStream
            val d = ByteArray(inStream.available())
            inStream.read(d)

            //write
            val outStream = FileOutputStream(url+"\\"+file.originalFilename)
            outStream.write(d)
            outStream.flush()
            outStream.close()*/

            Files.copy(file.inputStream,
                    Paths.get(url+File.separator+file.originalFilename),
                    StandardCopyOption.REPLACE_EXISTING)

            println(url)

            return true

        }catch (e: Exception){
            println(e.message)
        }

        return false
    }
}