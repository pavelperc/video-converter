package isd.videoConverter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import isd.videoConverter.settingsJson.ConversionSettings
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RequestParam
import ws.schild.jave.*
import java.io.*


// testing may be seen in test_request.rest (works only with Intellij Idea Ultimate)

@RestController
@RequestMapping("/api")
class MainController {
    companion object {
        val log = LoggerFactory.getLogger(MainController::class.java)
        
        init {
            log.debug("loaded MainController")
        }
    }
    
    @GetMapping("/convert")
    fun getMapping(): String {
        return "Hello, use post request."
    }
    
    
    @PostMapping("/convert")
    fun convert(
            @RequestParam("file") file: MultipartFile,
            @RequestParam("settings") settingsStr: String,
            response: HttpServletResponse
    ) {
        log.debug("in convert")
        log.debug("got settings {}", settingsStr)
        val mapper = ObjectMapper()
        val settings = try {
            mapper.readValue(settingsStr, ConversionSettings::class.java)
        } catch (e: Exception) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            log.error("Json parsing error", e)
            response.outputStream.println("Json Parsing Error: $e")
            return
        }
        
        log.debug("converting settings: {}", settings)
        log.debug("converting file name: {}", file.originalFilename)
        log.debug("converting file size: {}", file.size)
        
        
        response.setContentType("video/*")
        
        val (name, extension) =
                if (file.originalFilename?.contains('.') == true)
                    (file.originalFilename!!).split(".")
                else listOf(file.originalFilename ?: "file", "unknown")
    
        try {
            val filename = "converted-$name." + (settings.format ?: extension)
            response.setHeader("Content-Disposition", "attachment; filename=\"$filename\"");
            convertFile(file, settings, response.outputStream)
            response.flushBuffer()
        } catch (e: Exception) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            log.error("Conversion error", e)
            response.outputStream.println("Conversion Error: $e")
            return
        }
    }
    
    /** Returns converted file to outputStream. */
    fun convertFile(inputMultipartFile: MultipartFile, settings: ConversionSettings, outputStream: OutputStream) {
        
        val audio = AudioAttributes()
        val video = VideoAttributes()
        val attrs = EncodingAttributes()
        attrs.audioAttributes = audio
        attrs.videoAttributes = video
        
        settings.format?.let { attrs.format = it }
        
        video.setCodec(settings.video?.codec ?: VideoAttributes.DIRECT_STREAM_COPY)
        settings.video?.bitRate?.let { video.setBitRate(it) }
        settings.video?.frameRate?.let { video.setFrameRate(it) }
        
        settings.audio?.bitRate?.let { audio.setBitRate(it) }
        settings.audio?.codec?.let { audio.setCodec(it) }
        settings.audio?.samplingRate?.let { audio.setSamplingRate(it) }
        
        val instance = Encoder()
        
        val tempDir = File("temp")
        tempDir.mkdir()
        log.debug("temp dir: {}", tempDir.absolutePath)
        
        val tempOutputFile = File.createTempFile("converted-", "-" + inputMultipartFile.originalFilename, tempDir)
        val tempInputFile = File.createTempFile("input-", "-" + inputMultipartFile.originalFilename, tempDir)
        tempInputFile.writeBytes(inputMultipartFile.bytes)
        
        tempOutputFile.deleteOnExit()
        instance.encode(MultimediaObject(tempInputFile), tempOutputFile, attrs, null)
        log.debug("encoded to file {}. Size is {}", tempOutputFile.name, tempOutputFile.length())
        
        tempOutputFile.inputStream().copyTo(outputStream)

//        val deletedOutput = tempOutputFile.delete()
//        val deletedInput = tempInputFile.delete()
//        log.debug("temp files deleted: input:{} output:", deletedInput, deletedOutput)
    }
    
}
