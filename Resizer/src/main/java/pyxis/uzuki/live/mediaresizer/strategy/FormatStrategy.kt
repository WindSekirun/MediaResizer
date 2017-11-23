package pyxis.uzuki.live.mediaresizer.strategy

import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Log
import net.ypresto.androidtranscoder.format.MediaFormatExtraConstants
import net.ypresto.androidtranscoder.format.MediaFormatStrategy
import net.ypresto.androidtranscoder.format.OutputFormatUnavailableException

/**
 * MediaResizer
 * Class: FormatStrategy
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */
abstract class FormatStrategy(videoBitrate: Int = 8000 * 1000, audioBitrate: Int = -1, audioChannels: Int = -1) : MediaFormatStrategy {
    private var mVideoBitrate: Int = videoBitrate
    private var mAudioBitrate: Int = audioBitrate
    private var mAudioChannels: Int = audioChannels

    abstract fun getLogTag(): String
    abstract fun getLongerLength(): Int
    abstract fun getShorterLength(): Int

    override fun createVideoOutputFormat(inputFormat: MediaFormat): MediaFormat? {
        val width = inputFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height = inputFormat.getInteger(MediaFormat.KEY_HEIGHT)
        val longer: Int
        val shorter: Int
        val outWidth: Int
        val outHeight: Int
        if (width >= height) {
            longer = width
            shorter = height
            outWidth = getLongerLength()
            outHeight = getShorterLength()
        } else {
            shorter = width
            longer = height
            outWidth = getShorterLength()
            outHeight = getLongerLength()
        }
        if (longer * 9 != shorter * 16) {
            throw OutputFormatUnavailableException("This video is not 16:9, and is not able to transcode. (" + width + "x" + height + ")")
        }
        if (shorter <= getShorterLength()) {
            Log.d(getLogTag(), "This video is less or equal to target resolution, pass-through. (" + width + "x" + height + ")")
            return null
        }
        val format = MediaFormat.createVideoFormat("video/avc", outWidth, outHeight)

        format.setInteger(MediaFormat.KEY_BIT_RATE, mVideoBitrate)
        format.setInteger(MediaFormat.KEY_FRAME_RATE, 30)
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 3)
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        return format
    }

    override fun createAudioOutputFormat(inputFormat: MediaFormat): MediaFormat? {
        if (mAudioBitrate == -1 || mAudioChannels == -1)
            return null

        val format = MediaFormat.createAudioFormat(MediaFormatExtraConstants.MIMETYPE_AUDIO_AAC,
                inputFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE), mAudioChannels)
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)
        format.setInteger(MediaFormat.KEY_BIT_RATE, mAudioBitrate)
        return format
    }

}