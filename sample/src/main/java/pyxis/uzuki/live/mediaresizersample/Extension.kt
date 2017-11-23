package pyxis.uzuki.live.mediaresizersample

import android.media.MediaMetadataRetriever
import android.net.Uri


/**
 * MediaResizer
 * Class: Extension
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */

fun String.toUri(): Uri = Uri.parse(this)

fun String.getVideoWidth(): Int {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource("/path/to/video.mp4")
    val width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH))
    retriever.release()

    return width
}

fun String.getVideoHeight(): Int {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource("/path/to/video.mp4")
    val height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT))
    retriever.release()

    return height
}