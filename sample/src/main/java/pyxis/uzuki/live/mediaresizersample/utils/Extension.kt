package pyxis.uzuki.live.mediaresizersample.utils

import android.media.MediaMetadataRetriever
import android.net.Uri
import pyxis.uzuki.live.richutilskt.utils.toFile


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
    retriever.setDataSource(this)
    val width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH))
    retriever.release()
    return width
}

fun String.getVideoHeight(): Int {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(this)
    val height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT))
    retriever.release()
    return height
}

fun String.getSizeByMb(): String {
    val file = this.toFile()
    var size: Long = 0

    if (file.exists() && file.canRead()) {
        size = file.length()
    }

    return size.toNumInUnits()
}

fun Long.toNumInUnits(): String {
    var bytes = this
    var u = 0
    while (bytes > 1024 * 1024) {
        u++
        bytes = bytes shr 10
    }
    if (bytes > 1024) {
        u++
    }
    return String.format("%.1f %cB", bytes / 1024f, " kMGTPE"[u])
}