package isd.video_converter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VideoConverterApplication

fun main(args: Array<String>) {
    runApplication<VideoConverterApplication>(*args)
}
