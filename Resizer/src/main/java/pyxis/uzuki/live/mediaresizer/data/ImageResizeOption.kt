package pyxis.uzuki.live.mediaresizer.data

import android.graphics.Bitmap
import pyxis.uzuki.live.mediaresizer.model.ImageMode
import pyxis.uzuki.live.mediaresizer.model.ScanRequest

/**
 * MediaResizer
 * Class: ImageResizeOption
 * Created by Pyxis on 2017-11-27.
 *
 * Description:
 */

data class ImageResizeOption(val mode: ImageMode, val imageResolution: Pair<Int, Int>,
                             val bitmapFilter: Boolean, val format: Bitmap.CompressFormat,
                             val compressQuality: Int, val request: ScanRequest) {
    class Builder {
        private var mode: ImageMode = ImageMode.ResizeAndCompress
        private var imageResolution: Pair<Int, Int> = 1280 to 720
        private var bitmapFilter: Boolean = true
        private var format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
        private var compressQuality: Int = 100
        private var request: ScanRequest = ScanRequest.FALSE

        /**
         * set the mode to work with Image Resizing.
         * It have three options to set, default is ResizeAndCompress.
         * see [ImageMode] class to see all of available options.
         *
         * @param[mode] desire mode to process
         */
        fun setImageProcessMode(mode: ImageMode) = apply { this.mode = mode }

        /**
         * set desire width/height to resize image.
         * It keep aspect ratio itself.
         *
         * @param[width] desire width
         * @param[height] desire height
         */
        fun setImageResolution(width: Int, height: Int) = apply { this.imageResolution = width to height }

        /**
         * set filter flag for better quality.
         * true if the source should be filtered.
         *
         * @param[filter] filter flag
         */
        fun setBitmapFilter(filter: Boolean) = apply { this.bitmapFilter = filter }

        /**
         * set compress format and extension of output of resizing.
         * it have three options to set, default is Bitmap.CompressFormat.JPEG
         * see [Bitmap.CompressFormat] class to see all of available options.
         *
         * @param[format] desire compress format
         */
        fun setCompressFormat(format: Bitmap.CompressFormat) = apply { this.format = format }

        /**
         * set compress quality of output of resizing.
         * range is 0 and 100, else it throw [IllegalArgumentException]
         *
         * @param[compressQuality] desire compress quality
         */
        fun setCompressQuality(compressQuality: Int): Builder {
            if (compressQuality < 0 || compressQuality > 100) {
                throw IllegalArgumentException("The value must be between 0 and 100.")
            }

            return apply { this.compressQuality = compressQuality }
        }

        /**
         * set flag for requesting media scanning.
         * to use this settings, please add follow statement in your Application class.
         * MediaResizerGlobal.initializeApplication(this)
         *
         * @param[request] media scanning flag.
         */
        fun setScanRequest(request: ScanRequest) = apply { this.request = request }

        /**
         * Build [ImageResizeOption] object.
         */
        fun build() = ImageResizeOption(mode, imageResolution, bitmapFilter, format, compressQuality, request)
    }
}