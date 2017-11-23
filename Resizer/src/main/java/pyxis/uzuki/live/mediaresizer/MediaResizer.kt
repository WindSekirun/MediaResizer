package pyxis.uzuki.live.mediaresizer

import android.graphics.Bitmap
import android.util.Log
import net.ypresto.androidtranscoder.MediaTranscoder
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType
import pyxis.uzuki.live.mediaresizer.strategy.P480Strategy
import pyxis.uzuki.live.mediaresizer.strategy.P720Strategy
import pyxis.uzuki.live.richutilskt.utils.*
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception

/**
 * MediaResizer
 * Class: MediaResizer
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */
object MediaResizer {

    const val RESIZE_SUCCESS = 1
    const val RESIZE_FAILED = -1

    fun process(option: ResizeOption) {
        when (option.mediaType) {
            MediaType.IMAGE -> resizeImage(option)
            MediaType.VIDEO -> resizeVideo(option)
        }
    }

    private fun resizeVideo(option: ResizeOption) {
        val descriptor = getFileDescriptor(option.targetPath)
        val strategy = if (option.videoResolutionType == VideoResolutionType.P480)
            P480Strategy(1000 * 1000, 128 * 1000, 1)
        else
            P720Strategy(1000 * 1000, 128 * 1000, 1)

        val file = option.outputPath.toFile()

        try {
            file.createNewFile()
        } catch (e: IOException) {
            Log.d(MediaResizer.javaClass.simpleName, "Resizer failed: %s".format(e.message ?: ""))
            option.callback(RESIZE_FAILED, "")
        }

        MediaTranscoder.getInstance().transcodeVideo(descriptor, option.outputPath, strategy, object : MediaTranscoder.Listener {
            override fun onTranscodeProgress(progress: Double) {

            }

            override fun onTranscodeCanceled() {
                option.callback(RESIZE_FAILED, "")
            }

            override fun onTranscodeFailed(exception: Exception?) {
                Log.d(MediaResizer.javaClass.simpleName, "Resizer failed: %s".format(exception?.message ?: ""))
                option.callback(RESIZE_FAILED, "")
            }

            override fun onTranscodeCompleted() {
                option.callback(RESIZE_SUCCESS, option.outputPath)
            }
        })
    }

    private fun getFileDescriptor(path: String): FileDescriptor? {
        val file = path.toFile()
        return try {
            val stream = FileInputStream(file)
            stream.fd
        } catch (e: IOException) {
            null
        }
    }

    private fun resizeImage(option: ResizeOption) {
        val bitmap = option.targetPath.getBitmap() as Bitmap
        val degree = getPhotoOrientationDegree(option.targetPath)
        val rotated = rotate(bitmap, degree)

        val imageFile = option.outputPath.toFile()

        val newBitmap: Bitmap = if (rotated.width < rotated.height) {
            resizeImage(rotated, 720, 1280)
        } else {
            resizeImage(rotated, 1280, 720)
        }

        imageFile.saveBitmapToFile(newBitmap)

        runDelayed({ option.callback(RESIZE_SUCCESS, imageFile.absolutePath) }, 500)
    }

    private fun resizeImage(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
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
}