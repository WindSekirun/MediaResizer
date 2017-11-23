@file:JvmName("ResultBuilder")
@file:JvmMultifileClass

package pyxis.uzuki.live.mediaresizersample

import pyxis.uzuki.live.richutilskt.utils.getImageHeight
import pyxis.uzuki.live.richutilskt.utils.getImageMimeType
import pyxis.uzuki.live.richutilskt.utils.getImageWidth

/**
 * MediaResizer
 * Class: ResultBuilder
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */

fun displayImageResult(code: Int, original: String, output: String): String {
    return build {
        section("MediaResizer Result") {
            text("result is %d", value = code)
            text("Original Path: %s", value = original)
            text("Output Path: %s", value = output)
        }

        section("Original") {
            text("width: %s", value = original.getImageWidth())
            text("height: %s", value = original.getImageHeight())
            text("mimetype: %s", value = original.getImageMimeType())
        }

        section("Resized") {
            text("width: %s", value = output.getImageWidth())
            text("height: %s", value = output.getImageHeight())
            text("mimetype: %s", value = output.getImageMimeType())
        }
    }
}

fun displayVideoResult(code: Int, original: String, output: String): String {
    return build {
        section("MediaResizer Result") {
            text("result is %d", value = code)
            text("Original Path: %s", value = original)
            text("Output Path: %s", value = output)
        }

        section("Original") {
            text("width: %s", value = original.getVideoWidth())
            text("height: %s", value = original.getVideoHeight())
            text("mimetype: %s", value = original.getImageMimeType())
        }

        section("Resized") {
            text("width: %s", value = output.getVideoWidth())
            text("height: %s", value = output.getVideoHeight())
            text("mimetype: %s", value = output.getImageMimeType())
        }
    }
}

fun build(action: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.action()
    return sb.toString()
}

fun section(name: String = "Section", action: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.newLine()
    sb.text("========== %s ==========", value = name)
    sb.action()
    sb.newLineFeed()
    return sb.toString()
}

fun StringBuilder.newLine() = this.append("\n")

fun StringBuilder.text(append: String, newLine: Boolean = true): StringBuilder {
    return if (newLine) {
        this.append(append).newLine()
    } else {
        this.append(append)
    }
}

fun StringBuilder.text(append: String, newLine: Boolean = true, vararg value: Any): StringBuilder {
    return if (newLine) {
        this.append(append.format(value)).newLine()
    } else {
        this.append(append.format(value))
    }
}

fun StringBuilder.newLineFeed(number: Int = 20): StringBuilder {
    var line = ""
    for (i in 0 until number) {
        line += "="
    }
    return this.append(line).newLine()
}