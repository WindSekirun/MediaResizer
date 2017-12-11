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

        /**
         * set resolution to transcode video.
         * It have four options to set, default is [VideoResolutionType.AS720]
         * see [VideoResolutionType] to see all of available options.
         *
         * @param[resolutionType] desire resolution
         */
        fun setVideoResolutionType(resolutionType: VideoResolutionType) = apply { this.resolutionType = resolutionType }

        /**
         * set desire bitrate of video
         * default is 1000 * 1000 (1000kbps)
         *
         * @param[videoBitrate] desire bitrate of video
         */
        fun setVideoBitrate(videoBitrate: Int) = apply { this.videoBitrate = videoBitrate }

        /**
         * set desire bitrate of audio
         * default is 128 * 1000 (128kbps)
         *
         * -1 if the source should not transcode audio
         *
         * @param[audioBitrate] desire bitrate of audio
         */
        fun setAudioBitrate(audioBitrate: Int) = apply { this.audioBitrate = audioBitrate }

        /**
         * set desire channel of audio
         * default is 1, MONO Channel.
         *
         * -1 if the source should not transcode audio
         *
         * @param[audioChannel] desire channel of audio
         */
        fun setAudioChannel(audioChannel: Int) = apply { this.audioChannel = audioChannel }

        /**
         * set custom strategy of [android-transcoder][https://github.com/ypresto/android-transcoder]
         * MediaResizer contains all of available strategy in [pyxis.uzuki.live.mediaresizer.strategy] package,
         * but you may need customize transcoding strategy.
         * just pass your [MediaFormatStrategy] class.
         *
         * @param[customStrategy] strategy class which extends [MediaFormatStrategy]
         */
        fun setCustomStrategy(customStrategy: MediaFormatStrategy) = apply { this.customStrategy = customStrategy }

        /**
         * set flag for requesting media scanning.
         * to use this settings, please add follow statement in your Application class.
         * MediaResizerGlobal.initializeApplication(this)
         *
         * @param[request] media scanning flag.
         */
        fun setScanRequest(request: ScanRequest) = apply { this.request = request }

        /**
         * Build [VideoResizeOption] object.
         */
        fun build() = VideoResizeOption(resolutionType, videoBitrate, audioBitrate, audioChannel, customStrategy, request)
    }
}