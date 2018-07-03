package pyxis.uzuki.live.mediaresizer

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object MediaResizerGlobal {
    internal var context: Context? = null

    @JvmStatic
    fun initializeApplication(context: Context) {
        MediaResizerGlobal.context = context
    }
}

fun <R> useGlobalContext(action: Context.() -> R): R {
    val context = MediaResizerGlobal.context ?:
            throw NotInitializedException("Not initialized global Context. Please add MediaResizerGlobal.initializeApplication(this) in Application Class")
    return action(context)
}

class NotInitializedException(override var message: String) : Exception()