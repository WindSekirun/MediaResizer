package pyxis.uzuki.live.mediaresizer.strategy

import android.content.ContentValues.TAG
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Log
import net.ypresto.androidtranscoder.format.MediaFormatExtraConstants
import net.ypresto.androidtranscoder.format.MediaFormatPresets
import net.ypresto.androidtranscoder.format.MediaFormatStrategy


/**
 * MediaResizer
 * Class: AS960Strategy
 * Created by Pyxis on 2017-11-23.
 *
 * Description:
 */
class AS960Strategy(audioBitrate: Int = -1, audioChannels: Int = -1) : MediaFormatStrategy {
    private var mAudioBitrate: Int = audioBitrate
    private var mAudioChannels: Int = audioChannels

    override fun createVideoOutputFormat(inputFormat: MediaFormat): MediaFormat? {
        // TODO: detect non-baseline profile and throw exception
        val width = inputFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height = inputFormat.getInteger(MediaFormat.KEY_HEIGHT)
        val outputFormat = MediaFormatPresets.getExportPreset960x540(width, height)
        val outWidth = outputFormat.getInteger(MediaFormat.KEY_WIDTH)
        val outHeight = outputFormat.getInteger(MediaFormat.KEY_HEIGHT)
        Log.d(TAG, String.format("inputFormat: %dx%d => outputFormat: %dx%d", width, height, outWidth, outHeight))
        return outputFormat
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