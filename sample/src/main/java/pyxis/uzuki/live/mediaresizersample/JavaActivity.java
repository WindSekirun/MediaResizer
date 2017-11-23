package pyxis.uzuki.live.mediaresizersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * MediaResizer
 * Class: JavaActivity
 * Created by Pyxis on 2017-11-23.
 * <p>
 * Description:
 */

public class JavaActivity extends AppCompatActivity {
    private Button btnCamera;
    private Button btnVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }
}
