package pyxis.uzuki.live.mediaresizer

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pyxis.uzuki.live.richutilskt.utils.*
import java.io.File


class MainActivity : AppCompatActivity() {
    private var filePath = "";
    private var isPicture = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrays = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        RPermission.instance.checkPermission(this, arrays)

        btnCamera.setOnClickListener {
            RPickMedia.instance.pickFromCamera(this, { code, path ->
                if (code == RPickMedia.PICK_FAILED)
                    return@pickFromCamera

                rotate(Uri.parse(path) getRealPath this)
            })
        }

        btnVideo.setOnClickListener {
            RPickMedia.instance.pickFromVideoCamera(this, { code, path ->
                if (code == RPickMedia.PICK_FAILED)
                    return@pickFromVideoCamera

                processVideoResize(Uri.parse(path) getRealPath this)
            })
        }
    }

    fun rotate(path: String) {
        val bitmap = path.getBitmap() as Bitmap
        val degree = getPhotoOrientationDegree(path)
        val rotated = rotate(bitmap, degree)

        processImageResize(rotated)
    }

    fun processImageResize(src: Bitmap) {
        val file = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/resize/".toFile()
        file.mkdirs()
        val imageFile = File(file, "${System.currentTimeMillis().asDateString("yyyy-MM-dd HH:mm:ss")}.jpg")

        val newBitmap: Bitmap = if (src.width < src.height) {
            resize(src, 720, 1280)
        } else {
            resize(src, 1280, 720)
        }

        imageFile.saveBitmapToFile(newBitmap)
        isPicture = true
        filePath = imageFile.absolutePath ?: ""
    }

    fun processVideoResize(path: String) {
        val transcodingUtils = VideoTranscodingUtils(this, path)
        transcodingUtils.transcode { resultCode, outPath ->
            if (resultCode == VideoTranscodingUtils.TRANSCODING_SUCCESS) {
                toast(outPath)
            } else {
                toast("failed" + outPath)
            }
        }
    }
}
