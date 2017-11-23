@file:JvmName("ResultBuilder")
@file:JvmMultifileClass

package pyxis.uzuki.live.mediaresizersample.utils

import pyxis.uzuki.live.richutilskt.utils.getImageHeight
import pyxis.uzuki.live.richutilskt.utils.getImageWidth

/**
 * MediaReSizer
 * Class: ResultBuilder
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */

val newLine = "\n"
fun sectionTitle(title: String = "Section") = "==== %s ====\n".format(title)

fun displayImageResult(code: Int, original: String, output: String): String {
    val stringBuilder = StringBuilder()
    stringBuilder.apply {
        append(sectionTitle("MediaReSizer Image Result"))
        append("    • Result is %s\n".format(code))
        append("    • Original Path: %s\n".format(original))
        append("    • Output Path: %s\n".format(output))
        append(newLine)
        append(sectionTitle("Original"))
        append("    • Width : %s\n".format(original.getImageWidth()))
        append("    • Height : %s\n".format(original.getImageHeight()))
        append("    • Size : %s\n".format(original.getSizeByMb()))
        append(newLine)
        append(sectionTitle("ReSized"))
        append("    • Width : %s\n".format(output.getImageWidth()))
        append("    • Height : %s\n".format(output.getImageHeight()))
        append("    • Size : %s\n".format(output.getSizeByMb()))
    }

    return stringBuilder.toString()
}

fun displayVideoResult(code: Int, original: String, output: String): String {
    val stringBuilder = StringBuilder()
    stringBuilder.apply {
        append(sectionTitle("MediaReSizer Video Result"))
        append("    • Result is %s\n".format(code))
        append("    • Original Path: %s\n".format(original))
        append("    • Output Path: %s\n".format(output))
        append(newLine)
        append(sectionTitle("Original"))
        append("    • Width : %s\n".format(original.getVideoWidth()))
        append("    • Height : %s\n".format(original.getVideoHeight()))
        append("    • Size : %s\n".format(original.getSizeByMb()))
        append(newLine)
        append(sectionTitle("ReSized"))
        append("    • Width : %s\n".format(output.getVideoWidth()))
        append("    • Height : %s\n".format(output.getVideoHeight()))
        append("    • Size : %s\n".format(output.getSizeByMb()))
    }

    return stringBuilder.toString()
}