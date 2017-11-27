package pyxis.uzuki.live.mediaresizer

import android.graphics.Bitmap
import java.io.File

/**
 * MediaResizer
 * Class: Utils
 * Created by Pyxis on 2017-11-27.
 *
 * Description:
 */

fun File.saveBitmapToFile(bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): File? {
    this.outputStream().use {
        bitmap.compress(format, quality, it)
    }

    if (!bitmap.isRecycled) {
        bitmap.recycle()
    }
    return this
}

