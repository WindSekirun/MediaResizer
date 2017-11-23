package pyxis.uzuki.live.mediaresizer

import android.app.Activity
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType

/**
 * MediaResizer
 * Class: ResizeOption
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */
data class ResizeOption(val activity: Activity, val mediaType: MediaType, val videoResolutionType: VideoResolutionType,
                        val imageResolution: Pair<Int, Int>, val targetPath: String,
                        val outputPath: String, val callback: (Int, String) -> Unit) {

    class Builder {
        var activity: Activity? = null
        var mediaType: MediaType = MediaType.IMAGE
        var videoResolutionType: VideoResolutionType = VideoResolutionType.P480
        var imageResolution: Pair<Int, Int> = 1280 to 720
        var outputPath: String = ""
        var targetPath: String = ""
        var callback: (Int, String) -> Unit = { _, _ -> }

        fun setActivity(activity: Activity): Builder {
            this.activity = activity
            return this
        }

        fun setMediaType(mediaType: MediaType): Builder {
            this.mediaType = mediaType
            return this
        }

        fun setVideoResolutionType(videoResolutionType: VideoResolutionType): Builder {
            this.videoResolutionType = videoResolutionType
            return this
        }

        fun setImageResolution(width: Int, height: Int): Builder {
            this.imageResolution = width to height
            return this
        }

        fun setOutputPath(outputPath: String): Builder {
            this.outputPath = outputPath
            return this
        }

        fun setTargetPath(targetPath: String): Builder {
            this.targetPath = targetPath
            return this
        }

        fun setCallback(callback: (Int, String) -> Unit): Builder {
            this.callback = callback
            return this
        }

        fun build(): ResizeOption =
                ResizeOption(activity as Activity, mediaType, videoResolutionType, imageResolution, targetPath, outputPath, callback)
    }
}