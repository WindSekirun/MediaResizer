package pyxis.uzuki.live.mediaresizer

import android.graphics.Bitmap

fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
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
    val newImage = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
    if (!image.isRecycled) {
        image.recycle()
    }
    return newImage
}