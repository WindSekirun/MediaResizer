package pyxis.uzuki.live.mediaresizer.processor

import android.util.Log
import net.ypresto.androidtranscoder.MediaTranscoder
import net.ypresto.androidtranscoder.format.MediaFormatStrategy
import pyxis.uzuki.live.mediaresizer.BuildConfig
import pyxis.uzuki.live.mediaresizer.MediaResizer
import pyxis.uzuki.live.mediaresizer.MediaResizer.executeCallback
import pyxis.uzuki.live.mediaresizer.data.ResizeOption
import pyxis.uzuki.live.mediaresizer.data.VideoResizeOption
import pyxis.uzuki.live.mediaresizer.model.ScanRequest
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType
import pyxis.uzuki.live.mediaresizer.strategy.AS480Strategy
import pyxis.uzuki.live.mediaresizer.strategy.AS720Strategy
import pyxis.uzuki.live.mediaresizer.strategy.AS960Strategy
import pyxis.uzuki.live.mediaresizer.useGlobalContext
import pyxis.uzuki.live.richutilskt.utils.requestMediaScanner
import pyxis.uzuki.live.richutilskt.utils.toFile
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception

/**
 * MediaResizer
 * Class: VideoProcessor
 * Created by Pyxis on 2017-11-27.
 *
 * Description:
 */
internal fun resizeVideo(option: ResizeOption) {
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
            if (option.videoResizeOption?.request ?: ScanRequest.FALSE == ScanRequest.TRUE) {
                useGlobalContext { requestMediaScanner(option.outputPath) }
            }

            option.executeCallback(true, option.outputPath)
        }
    })
}

internal fun getFileDescriptor(path: String): FileDescriptor? {
    val file = path.toFile()
    return try {
        val stream = FileInputStream(file)
        stream.fd
    } catch (e: IOException) {
        null
    }
}

internal fun getTranscodingStrategy(resizeOption: VideoResizeOption): MediaFormatStrategy =
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