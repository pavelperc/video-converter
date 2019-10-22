package isd.video_converter

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBePositive
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import ws.schild.jave.*
import java.io.File


class ConversionTest {
    
    // https://github.com/a-schild/jave2/wiki/Examples
    @Test
    fun testConversion() {
        println("working dir: ${System.getProperty("user.dir")}")
        val source = File("test_video/bunny.mp4")
        val target = File("test_video/bunny-converted.avi")
        target.delete()
        
        source.exists().shouldBeTrue()
        source.length().shouldBePositive()
        target.exists().shouldBeFalse()
        
        val audio = AudioAttributes()
        val video = VideoAttributes()
        val attrs = EncodingAttributes()
        attrs.audioAttributes = audio
        attrs.videoAttributes = video
        
        // for speed-up
        video.setCodec(VideoAttributes.DIRECT_STREAM_COPY)
        attrs.format = "avi"
        val instance = Encoder()
        instance.encode(MultimediaObject(source), target, attrs, null)
        
        source.exists().shouldBeTrue()
        target.exists().shouldBeTrue()
        target.length().shouldBePositive()
    }
}