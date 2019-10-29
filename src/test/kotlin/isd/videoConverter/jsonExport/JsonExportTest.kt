package isd.videoConverter.jsonExport

import isd.videoConverter.settingsJson.ConversionSettings
import isd.videoConverter.settingsJson.VideoSettings
import org.junit.Assert.*
import org.junit.Test
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationConfig
import org.springframework.boot.convert.ApplicationConversionService.configure


class JsonExportTest {
    
    @Test
    fun testExport() {
        val settings = ConversionSettings().apply {
            video = VideoSettings().apply {
                this.bitRate = 1
                this.codec = "mpeg4"
                this.frameRate = 10
            }
            format = "mp4"
        }
        
        val mapper = ObjectMapper()
        
        val str = mapper
//                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(settings)
        println(str)
    }
    
    @Test
    fun testMissing() {
        val mapper = ObjectMapper()
        
        val settings = mapper.readValue("""
            {"video":{"codec":"mpeg4"}}
        """.trimIndent(), ConversionSettings::class.java)
    
        println(settings)
    }
}