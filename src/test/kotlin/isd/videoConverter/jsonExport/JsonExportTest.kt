package isd.videoConverter.jsonExport

import com.fasterxml.jackson.databind.ObjectMapper
import isd.videoConverter.settingsJson.AudioSettings
import isd.videoConverter.settingsJson.ConversionSettings
import isd.videoConverter.settingsJson.VideoSettings
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test


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
            audio = AudioSettings().apply {
                bitRate = 64000
                samplingRate = 22050
                codec = "libmp3lame"
            }
        }
        
        val mapper = ObjectMapper()
        
        val str = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(settings)
        println(str)
        val settingsNew = mapper.readValue(str, ConversionSettings::class.java)
        settingsNew.toString() shouldEqual settings.toString()
    }
    
    @Test
    fun testMissing() {
        val mapper = ObjectMapper()
        
        val settings = mapper.readValue("""
            {"video":{"codec":"mpeg4"}}
        """.trimIndent(), ConversionSettings::class.java)
    
        println(settings)
        settings.audio.shouldBeNull()
        settings.format.shouldBeNull()
        settings.video.shouldNotBeNull().codec.shouldEqual("mpeg4")
    }
}