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

        /**
         * set media type to resize
         * It have two options to set, default is [MediaType.IMAGE]
         * see [MediaType] class to see all of available options.
         *
         * @param[mediaType] desire mediatype to resize
         */
        fun setMediaType(mediaType: MediaType) = apply { this.mediaType = mediaType }

        /**
         * set [VideoResizeOption] class to resize video.
         * set this methods when you use Video Resizing.
         *
         * @param[videoResizeOption] [VideoResizeOption] object.
         */
        fun setVideoResizeOption(videoResizeOption: VideoResizeOption?) = apply { this.videoResizeOption = videoResizeOption }

        /**
         * set [ImageResizeOption] class to resize image.
         * set this methods when you use Image Resizing.
         *
         * @param[imageResizeOption] [ImageResizeOption] object.
         */
        fun setImageResizeOption(imageResizeOption: ImageResizeOption?) = apply { this.imageResizeOption = imageResizeOption }

        /**
         * set output path for output file.
         *
         * @param[outputPath] desire output path
         */
        fun setOutputPath(outputPath: String) = apply { this.outputPath = outputPath }

        /**
         * set target path for input file.
         *
         * @param[targetPath] desire target file
         */
        fun setTargetPath(targetPath: String) = apply { this.targetPath = targetPath }

        /**
         * set callback for result.
         * this methods use higher-order functions. do not use this methods in java, use [setCallback] instead.
         *
         * @param[callback] higher-order functions which have two parameters, Int (resultCode) and String (path)
         */
        fun setCallback(callback: (Int, String) -> Unit) = apply { this.callback = callback }

        /**
         * set callback for result.
         * this methods use [F2] interface. use [setCallback] in Kotlin instead.
         *
         * @param[callback] interface which have two parameters
         */
        fun setCallback(callback: F2<Int, String>) = apply { this.callback = { int, string -> callback.invoke(int, string) } }

        /**
         * Build [ResizeOption] object.
         */
        fun build(): ResizeOption = ResizeOption(mediaType, videoResizeOption, imageResizeOption, targetPath, outputPath, callback)
    }
}