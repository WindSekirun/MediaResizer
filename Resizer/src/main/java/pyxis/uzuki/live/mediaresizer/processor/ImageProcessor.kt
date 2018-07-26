package pyxis.uzuki.live.mediaresizer.processor

import android.graphics.Bitmap
import pyxis.uzuki.live.mediaresizer.MediaResizer.executeCallback
import pyxis.uzuki.live.mediaresizer.data.ResizeOption
import pyxis.uzuki.live.mediaresizer.model.ImageMode
import pyxis.uzuki.live.mediaresizer.model.ScanRequest
import pyxis.uzuki.live.mediaresizer.saveBitmapToFile
import pyxis.uzuki.live.mediaresizer.useGlobalContext
import pyxis.uzuki.live.richutilskt.utils.*

/**
 * MediaResizer
 * Class: ImageProcessor
 * Created by Pyxis on 2017-11-27.
 *
 * Description:
 */

internal fun resizeImage(option: ResizeOption) {
    val bitmap = option.targetPath.getBitmap() as Bitmap
    val degree = getPhotoOrientationDegree(option.targetPath)
    val rotated = rotate(bitmap, degree)
    val imageFile = option.outputPath.toFile()
    val enableResize = option.imageResizeOption?.mode != ImageMode.CompressOnly

    val newBitmap: Bitmap = if (enableResize) {
        val pair = option.imageResizeOption?.imageResolution ?: 1280 to 720
        if (rotated.width < rotated.height) {
            resizeImage(rotated, pair.second, pair.first, option.imageResizeOption?.bitmapFilter
                    ?: true)
        } else {
            resizeImage(rotated, pair.first, pair.second, option.imageResizeOption?.bitmapFilter
                    ?: true)
        }
    } else {
        rotated
    }

    imageFile.saveBitmapToFile(newBitmap, option.imageResizeOption?.format
            ?: Bitmap.CompressFormat.JPEG,
            option.imageResizeOption?.compressQuality ?: 100)

    if (option.imageResizeOption?.request ?: ScanRequest.FALSE == ScanRequest.TRUE) {
        useGlobalContext { requestMediaScanner(imageFile.absolutePath) }
    }

    runDelayed(500) { option.executeCallback(true, imageFile.absolutePath) }
}

private fun resizeImage(image: Bitmap, maxWidth: Int, maxHeight: Int, filter: Boolean): Bitmap {
    if (!(maxHeight > 0 && maxWidth > 0)) {
        return image
    }

    val width = image.width
    val height = image.height
    val ratioBitmap = width.toFloat() / height.toFloat()
    val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

    var finalWidth = maxWidth
    var finalHeight = maxHeight

    if (ratioMax > ratioBitmap) {
        finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
    } else {
        finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
    }

    val newImage = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, filter)
    if (newImage != image && !image.isRecycled) {
        image.recycle()
    }

    return newImage
}