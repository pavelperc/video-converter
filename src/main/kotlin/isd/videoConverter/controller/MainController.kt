package isd.videoConverter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import isd.videoConverter.settingsJson.ConversionSettings
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RequestParam
import java.io.File


// testing may be seen in test_request.rest (works only with Intellij Idea Ultimate)

@RestController
@RequestMapping("/api")
class MainController {
    
    @GetMapping("/convert")
    fun getMapping(): String {
        return "Hello, use post request."
    }
    
    
    @PostMapping("/convert")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile, @RequestParam("settings") settingsStr: String): String {
        val mapper = ObjectMapper()
        val settings = mapper.readValue(settingsStr, ConversionSettings::class.java)
        
        println("settings: $settings")
        println("filename: " + file.originalFilename)
        return "got file of size: " + file.size
    }
    
}
