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

        fun setImageProcessMode(mode: ImageMode): Builder {
            this.mode = mode
            return this
        }

        fun setImageResolution(width: Int, height: Int): Builder {
            this.imageResolution = width to height
            return this
        }

        fun setBitmapFilter(filter: Boolean): Builder {
            this.bitmapFilter = filter
            return this
        }

        fun setCompressFormat(format: Bitmap.CompressFormat): Builder {
            this.format = format
            return this
        }

        fun setCompressQuality(compressQuality: Int): Builder {
            this.compressQuality = compressQuality
            return this
        }

        fun setScanRequest(request: ScanRequest): Builder {
            this.request = request
            return this
        }

        fun build() = ImageResizeOption(mode, imageResolution, bitmapFilter, format, compressQuality, request)
    }
}