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

        fun setMediaType(mediaType: MediaType) = apply { this.mediaType = mediaType }

        fun setVideoResizeOption(videoResizeOption: VideoResizeOption) = apply { this.videoResizeOption = videoResizeOption }

        fun setImageResizeOption(imageResizeOption: ImageResizeOption) = apply { this.imageResizeOption = imageResizeOption }

        fun setOutputPath(outputPath: String) = apply { this.outputPath = outputPath }

        fun setTargetPath(targetPath: String) = apply { this.targetPath = targetPath }

        fun setCallback(callback: (Int, String) -> Unit) = apply { this.callback = callback }

        fun setCallback(callback: F2<Int, String>) = apply { this.callback = { int, string -> callback.invoke(int, string) } }

        fun build(): ResizeOption = ResizeOption(mediaType, videoResizeOption, imageResizeOption, targetPath, outputPath, callback)
    }
}