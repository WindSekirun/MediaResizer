package pyxis.uzuki.live.mediaresizer.data

import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.richutilskt.impl.F2

/**
 * MediaResizer
 * Class: ResizeOption
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */
data class ResizeOption(val mediaType: MediaType, val videoResizeOption: VideoResizeOption?,
                        val imageResizeOption: ImageResizeOption?, val targetPath: String,
                        val outputPath: String, val callback: (Int, String) -> Unit) {

    class Builder {
        private var mediaType: MediaType = MediaType.IMAGE
        private var videoResizeOption: VideoResizeOption? = null
        private var imageResizeOption: ImageResizeOption? = null
        private var outputPath: String = ""
        private var targetPath: String = ""
        private var callback: (Int, String) -> Unit = { _, _ -> }

        fun setMediaType(mediaType: MediaType): Builder {
            this.mediaType = mediaType
            return this
        }

        fun setVideoResizeOption(videoResizeOption: VideoResizeOption): Builder {
            this.videoResizeOption = videoResizeOption
            return this
        }

        fun setImageResizeOption(imageResizeOption: ImageResizeOption): Builder {
            this.imageResizeOption = imageResizeOption
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

        fun setCallback(callback: F2<Int, String>): Builder {
            this.callback = { int, string -> callback.invoke(int, string) }
            return this
        }

        fun build(): ResizeOption =
                ResizeOption(mediaType, videoResizeOption, imageResizeOption, targetPath, outputPath, callback)
    }
}