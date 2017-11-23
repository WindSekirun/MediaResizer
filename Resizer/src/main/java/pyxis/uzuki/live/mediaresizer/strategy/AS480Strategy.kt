package pyxis.uzuki.live.mediaresizer.strategy

/**
 * MediaResizer
 * Class: AS480Strategy
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */

class AS480Strategy @JvmOverloads constructor(videoBitrate: Int = 8000 * 1000, audioBitrate: Int = -1, audioChannels: Int = -1)
    : FormatStrategy(videoBitrate, audioBitrate, audioChannels) {

    override fun getLongerLength(): Int = 854
    override fun getShorterLength(): Int = 480
    override fun getLogTag(): String = AS480Strategy::class.java.simpleName
}