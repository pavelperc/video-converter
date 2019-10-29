package isd.videoConverter.settingsJson


class AudioSettings {
    var codec: String? = null
    var bitRate: Int? = null
    var samplingRate: Int? = null
    
    override fun toString() = "AudioSettings(codec=$codec, bitRate=$bitRate, samplingRate=$samplingRate)"
}

class VideoSettings {
    var codec: String? = null
    var bitRate: Int? = null
    var frameRate: Int? = null
    
    override fun toString() = "VideoSettings(codec=$codec, bitRate=$bitRate, frameRate=$frameRate)"
}

class ConversionSettings {
    /** By default it is the same as original. */
    var format: String? = null
    
    var video: VideoSettings? = null
    var audio: AudioSettings? = null
    
    override fun toString() = "ConversionSettings(\nformat='$format',\nvideo=$video,\naudio=$audio\n)"
}