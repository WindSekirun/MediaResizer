package pyxis.uzuki.live.mediaresizersample.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pyxis.uzuki.live.mediaresizersample.R
import pyxis.uzuki.live.richutilskt.utils.RPermission

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrays = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)

        RPermission.instance.checkPermission(this, arrays)

        btnKotlin.setOnClickListener { startActivity(Intent(this, KotlinActivity::class.java)) }

        btnJava.setOnClickListener { startActivity(Intent(this, JavaActivity::class.java)) }
    }
}
