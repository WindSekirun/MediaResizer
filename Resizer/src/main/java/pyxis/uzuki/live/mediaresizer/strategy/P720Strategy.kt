package pyxis.uzuki.live.mediaresizer.strategy

/**
 * MediaResizer
 * Class: P720Strategy
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */

class P720Strategy @JvmOverloads constructor(videoBitrate: Int = 8000 * 1000, audioBitrate: Int = -1, audioChannels: Int = -1)
    : FormatStrategy(videoBitrate, audioBitrate, audioChannels) {

    override fun getLongerLength(): Int = 1280
    override fun getShorterLength(): Int = 720
    override fun getLogTag(): String = P720Strategy::class.java.simpleName
}