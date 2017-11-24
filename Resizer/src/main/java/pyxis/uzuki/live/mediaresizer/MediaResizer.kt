package pyxis.uzuki.live.mediaresizer

import android.graphics.Bitmap
import android.util.Log
import net.ypresto.androidtranscoder.MediaTranscoder
import net.ypresto.androidtranscoder.format.MediaFormatStrategy
import pyxis.uzuki.live.mediaresizer.data.ResizeOption
import pyxis.uzuki.live.mediaresizer.data.VideoResizeOption
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType
import pyxis.uzuki.live.mediaresizer.strategy.AS480Strategy
import pyxis.uzuki.live.mediaresizer.strategy.AS720Strategy
import pyxis.uzuki.live.mediaresizer.strategy.AS960Strategy
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

    @JvmStatic
    fun process(option: ResizeOption) {
        when (option.mediaType) {
            MediaType.IMAGE -> resizeImage(option)
            MediaType.VIDEO -> resizeVideo(option)
        }
    }

    private fun ResizeOption.executeCallback(isSuccess: Boolean, path: String) {
        val code = if (isSuccess) RESIZE_SUCCESS else RESIZE_FAILED
        runOnUiThread { callback(code, path) }
    }

    private fun resizeVideo(option: ResizeOption) {
        val descriptor = getFileDescriptor(option.targetPath)
        val resizeOption = option.videoResizeOption
        val strategy = if (resizeOption != null) {
            getTranscodingStrategy(resizeOption)
        } else {
            AS720Strategy(1000 * 1000, 128 * 1000, 1)
        }

        val file = option.outputPath.toFile()

        try {
            file.createNewFile()
        } catch (e: IOException) {
            if (BuildConfig.DEBUG)
                Log.d(MediaResizer.javaClass.simpleName, "Resizer failed: %s".format(e.message ?: ""))
            option.executeCallback(false, option.targetPath)
        }

        MediaTranscoder.getInstance().transcodeVideo(descriptor, option.outputPath, strategy, object : MediaTranscoder.Listener {
            override fun onTranscodeProgress(progress: Double) {

            }

            override fun onTranscodeCanceled() {
                option.executeCallback(false, option.targetPath)
            }

            override fun onTranscodeFailed(exception: Exception?) {
                if (BuildConfig.DEBUG)
                    Log.d(MediaResizer.javaClass.simpleName, "Resizer failed: %s".format(exception?.message ?: ""))
                option.executeCallback(false, option.targetPath)
            }

            override fun onTranscodeCompleted() {
                option.executeCallback(true, option.outputPath)
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

    private fun getTranscodingStrategy(resizeOption: VideoResizeOption): MediaFormatStrategy =
            when (resizeOption.resolutionType) {
                VideoResolutionType.AS480 ->
                    AS480Strategy(resizeOption.videoBitrate, resizeOption.audioBitrate, resizeOption.audioChannel)
                VideoResolutionType.AS720 ->
                    AS720Strategy(resizeOption.videoBitrate, resizeOption.audioBitrate, resizeOption.audioChannel)
                VideoResolutionType.AS960 ->
                    AS960Strategy(resizeOption.audioBitrate, resizeOption.audioChannel)
                VideoResolutionType.CUSTOM ->
                    resizeOption.customStrategy ?: AS720Strategy(resizeOption.videoBitrate, resizeOption.audioBitrate, resizeOption.audioChannel)
            }

    private fun resizeImage(option: ResizeOption) {
        val bitmap = option.targetPath.getBitmap() as Bitmap
        val degree = getPhotoOrientationDegree(option.targetPath)
        val rotated = rotate(bitmap, degree)

        val imageFile = option.outputPath.toFile()

        val newBitmap: Bitmap = if (rotated.width < rotated.height) {
            resizeImage(rotated, option.imageResolution.second, option.imageResolution.first)
        } else {
            resizeImage(rotated, option.imageResolution.first, option.imageResolution.second)
        }

        imageFile.saveBitmapToFile(newBitmap)

        runDelayed(500) { option.executeCallback(true, imageFile.absolutePath) }
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