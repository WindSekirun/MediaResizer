package pyxis.uzuki.live.mediaresizersample

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import pyxis.uzuki.live.mediaresizer.MediaResizer
import pyxis.uzuki.live.mediaresizer.ResizeOption
import pyxis.uzuki.live.mediaresizer.model.MediaType
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType
import pyxis.uzuki.live.richutilskt.utils.RPickMedia
import pyxis.uzuki.live.richutilskt.utils.asDateString
import pyxis.uzuki.live.richutilskt.utils.getRealPath
import pyxis.uzuki.live.richutilskt.utils.toFile
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
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/resize/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.jpg")

        val option = ResizeOption.Builder()
                .setActivity(this)
                .setImageResolution(1280, 720)
                .setMediaType(MediaType.IMAGE)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->
                    txtStatus.text = displayImageResult(code, path, output)
                })
                .build()

        MediaResizer.process(option)
    }

    private fun processVideo(path: String) {
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/resize/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.mp4")

        val option = ResizeOption.Builder()
                .setActivity(this)
                .setVideoResolutionType(VideoResolutionType.P480)
                .setMediaType(MediaType.VIDEO)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->
                    txtStatus.text = displayVideoResult(code, path, output)
                })
                .build()

        MediaResizer.process(option)
    }
}
