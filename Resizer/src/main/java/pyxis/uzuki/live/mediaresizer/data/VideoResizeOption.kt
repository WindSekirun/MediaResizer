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

        fun setVideoResolutionType(resolutionType: VideoResolutionType) = apply { this.resolutionType = resolutionType }

        fun setVideoBitrate(videoBitrate: Int) = apply { this.videoBitrate = videoBitrate }

        fun setAudioBitrate(audioBitrate: Int) = apply { this.audioBitrate = audioBitrate }

        fun setAudioChannel(audioChannel: Int) = apply { this.audioChannel = audioChannel }

        fun setCustomStrategy(customStrategy: MediaFormatStrategy) = apply { this.customStrategy = customStrategy }

        /**
         * set flag for requesting media scanning.
         * to use this settings, please add follow statement in your Application class.
         * MediaResizerGlobal.initializeApplication(this)
         *
         * @param[request] media scanning flag.
         */
        fun setScanRequest(request: ScanRequest) = apply { this.request = request }

        fun build() = VideoResizeOption(resolutionType, videoBitrate, audioBitrate, audioChannel, customStrategy, request)
    }
}