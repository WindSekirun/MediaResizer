package pyxis.uzuki.live.mediaresizer

import pyxis.uzuki.live.mediaresizer.data.ResizeOption
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.processor.resizeImage
import pyxis.uzuki.live.mediaresizer.processor.resizeImageSynchronously
import pyxis.uzuki.live.mediaresizer.processor.resizeVideo
import pyxis.uzuki.live.richutilskt.utils.runOnUiThread

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

    @JvmStatic
    fun processSynchronously(option: ResizeOption): Pair<Int, String> {
        return when (option.mediaType) {
            MediaType.IMAGE -> resizeImageSynchronously(option)
            MediaType.VIDEO -> throw NotImplementedError("Currently resizing video by synchronous is not supported.")
        }
    }

    fun ResizeOption.executeCallback(isSuccess: Boolean, path: String) {
        val code = if (isSuccess) RESIZE_SUCCESS else RESIZE_FAILED
        runOnUiThread { callback(code, path) }
    }
}