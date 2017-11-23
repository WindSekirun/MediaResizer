package pyxis.uzuki.live.mediaresizersample

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import pyxis.uzuki.live.mediaresizer.MediaResizer
import pyxis.uzuki.live.mediaresizer.ResizeOption
import pyxis.uzuki.live.mediaresizer.VideoResizeOption
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType
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

        val realPath = path.toUri() getRealPath this

        if (type == MediaType.VIDEO) {
            processVideo(realPath)
        } else {
            processImage(realPath)
        }
    }

    private fun processImage(path: String) {
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MediaResizer/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.jpg")
        val progress = progress("Encoding...")

        val option = ResizeOption.Builder()
                .setMediaType(MediaType.IMAGE)
                .setImageResolution(1280, 720)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->
                    txtStatus.text = displayImageResult(code, path, output)
                    progress.dismiss()
                })
                .build()

        MediaResizer.process(option)
    }

    private fun processVideo(path: String) {
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MediaResizer/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.mp4")
        val progress = progress("Encoding...")

        val resizeOption = VideoResizeOption.Builder()
                .setVideoResolutionType(VideoResolutionType.P480)
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
