package pyxis.uzuki.live.mediaresizer.data

import net.ypresto.androidtranscoder.format.MediaFormatStrategy
import pyxis.uzuki.live.mediaresizer.model.ScanRequest
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType

/**
 * MediaResizer
 * Class: VideoResizeOption
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */

data class VideoResizeOption(val resolutionType: VideoResolutionType, val videoBitrate: Int,
                             val audioBitrate: Int, val audioChannel: Int, val customStrategy: MediaFormatStrategy?,
                             val request: ScanRequest) {

    class Builder {
        private var resolutionType: VideoResolutionType = VideoResolutionType.AS720
        private var videoBitrate: Int = 1000 * 1000
        private var audioBitrate: Int = 128 * 1000
        private var audioChannel: Int = 1
        private var customStrategy: MediaFormatStrategy? = null
        private var request: ScanRequest = ScanRequest.FALSE

        fun setVideoResolutionType(resolutionType: VideoResolutionType): Builder {
            this.resolutionType = resolutionType
            return this
        }

        fun setVideoBitrate(videoBitrate: Int): Builder {
            this.videoBitrate = videoBitrate
            return this
        }

        fun setAudioBitrate(audioBitrate: Int): Builder {
            this.audioBitrate = audioBitrate
            return this
        }

        fun setAudioChannel(audioChannel: Int): Builder {
            this.audioChannel = audioChannel
            return this
        }

        fun setCustomStrategy(customStrategy: MediaFormatStrategy): Builder {
            this.customStrategy = customStrategy
            return this
        }

        fun setScanRequest(request: ScanRequest): Builder {
            this.request = request
            return this
        }

        fun build() = VideoResizeOption(resolutionType, videoBitrate, audioBitrate, audioChannel, customStrategy, request)
    }
}