package com.example.demo

import com.example.demo.helper.UploadHelper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.ClassPathResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@SpringBootApplication
class Demo1Application

fun main(args: Array<String>) {
    runApplication<Demo1Application>(*args)
}

@RestController
class Controller{
    private val temp = "Hello, "
    private lateinit var response: HashMap<String,Any>

    @GetMapping("/greet")
    fun msgG(@RequestParam(value = "name", defaultValue = "champions") name: String): String {
        return temp+name
    }

    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile): HashMap<String,Any>{
        response = HashMap()
        response["content-type"] = file.contentType.toString()

        //println(file.originalFilename+"\n"+file.size+"\n"+file.contentType+"\n"+file.name)
        //1.webp   93492   image/webp  img

        if(file.isEmpty) {
            response["status"] = false
            response["message"] = "No file found!"
        }

        else if(file.contentType?.contains("image/") == true) {
            try {
                if(UploadHelper("image").save(file)){
                    response["image"] = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(ClassPathResource("image/").path)
                            .path(file.originalFilename.toString())
                            .toUriString()
                    response["message"] = "image uploaded successfully!"
                    response["status"] = true
                }else{
                    response["status"] = false
                    response["message"] = "failed to upload!"
                }
            }catch (e: Exception){
                response["status"] = false
                response["message"] = e.message.toString()
            }
        }

        else if (file.contentType?.contains("video/") == true) {
            try {
                if(UploadHelper("video").save(file)){
                    response["video"] = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(ClassPathResource("video/").path)
                            .path(file.originalFilename.toString())
                            .toUriString()
                    response["status"] = true
                    response["message"] = "video uploaded successfully!"

                }else{
                    response["status"] = false
                    response["message"] = "failed to upload!"
                }
            }catch (e: Exception){
                response["status"] = false
                response["message"] = e.message.toString()
            }
        }

        else if (file.contentType?.contains("audio/") == true) {
            try {
                if(UploadHelper("audio").save(file)){
                    response["audio"] = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(ClassPathResource("audio/").path)
                            .path(file.originalFilename.toString())
                            .toUriString()

                    response["status"] = true
                    response["message"] = "music uploaded successfully!"

                }else{
                    response["status"] = false
                    response["message"] = "failed to upload!"
                }
            }catch (e: Exception){
                response["status"] = false
                response["message"] = e.message.toString()
            }
        }

        return response
    }

    @PostMapping("/user/insert")
    fun insert(){}

    @GetMapping("/user/get")
    fun getUser(){}
}