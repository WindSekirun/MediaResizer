package pyxis.uzuki.live.mediaresizersample.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import pyxis.uzuki.live.mediaresizer.MediaResizer
import pyxis.uzuki.live.mediaresizer.data.ImageResizeOption
import pyxis.uzuki.live.mediaresizer.data.ResizeOption
import pyxis.uzuki.live.mediaresizer.data.VideoResizeOption
import pyxis.uzuki.live.mediaresizer.model.ImageMode
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.model.ScanRequest
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType
import pyxis.uzuki.live.mediaresizersample.R
import pyxis.uzuki.live.mediaresizersample.utils.displayImageResult
import pyxis.uzuki.live.mediaresizersample.utils.displayVideoResult
import pyxis.uzuki.live.richutilskt.utils.*
import java.io.File

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btnCamera.setOnClickListener {
            RPickMedia.instance.pickFromCamera(this, { code, path -> resultProcess(code, path, MediaType.IMAGE) })
        }

        btnVideo.setOnClickListener {
            RPickMedia.instance.pickFromVideoCamera(this, { code, path -> resultProcess(code, path, MediaType.VIDEO) })
        }

        btnGallery.setOnClickListener {
            RPickMedia.instance.pickFromGallery(this, { code, path -> resultProcess(code, path, MediaType.IMAGE) })
        }

        btnVideoGallery.setOnClickListener {
            RPickMedia.instance.pickFromVideo(this, { code, path -> resultProcess(code, path, MediaType.VIDEO) })
        }
    }

    private fun resultProcess(code: Int, path: String, type: MediaType) {
        if (code == RPickMedia.PICK_FAILED) {
            return
        }

        if (type == MediaType.VIDEO) {
            selectVideoStrategy(path)
        } else {
            processImage(path)
        }
    }

    private fun processImage(path: String) {
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MediaResizer/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.jpg")
        val progress = progress("Encoding...")

        val resizeOption = ImageResizeOption.Builder()
                .setImageProcessMode(ImageMode.ResizeAndCompress)
                .setImageResolution(1280, 720)
                .setBitmapFilter(false)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setCompressQuality(75)
                .setScanRequest(ScanRequest.TRUE)
                .build()

        val option = ResizeOption.Builder()
                .setMediaType(MediaType.IMAGE)
                .setImageResizeOption(resizeOption)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->
                    txtStatus.text = displayImageResult(code, path, output)
                    progress.dismiss()
                })
                .build()

        MediaResizer.process(option)
    }

    private fun selectVideoStrategy(path: String) {
        val lists = arrayListOf("480P", "720P", "960x540 (not supported in devices)")
        selector(lists, { dialog, item, position ->
            val type: VideoResolutionType = when (position) {
                0 -> VideoResolutionType.AS480
                1 -> VideoResolutionType.AS720
                2 -> VideoResolutionType.AS960
                else -> VideoResolutionType.AS720
            }
            processVideo(path, type)
            dialog.dismiss()
        })
    }

    private fun processVideo(path: String, type: VideoResolutionType) {
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MediaResizer/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.mp4")
        val progress = progress("Encoding...")

        val resizeOption = VideoResizeOption.Builder()
                .setVideoResolutionType(type)
                .setVideoBitrate(1000 * 1000)
                .setAudioBitrate(128 * 1000)
                .setAudioChannel(1)
                .setScanRequest(ScanRequest.TRUE)
                .build()

        val option = ResizeOption.Builder()
                .setMediaType(MediaType.VIDEO)
                .setVideoResizeOption(resizeOption)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->
                    txtStatus.text = displayVideoResult(code, path, output)
                    progress.dismiss()
                })
                .build()

        MediaResizer.process(option)
    }
}
