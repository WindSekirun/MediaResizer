package pyxis.uzuki.live.mediaresizersample;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.io.File;

import pyxis.uzuki.live.mediaresizer.MediaResizer;
import pyxis.uzuki.live.mediaresizer.ResizeOption;
import pyxis.uzuki.live.mediaresizer.model.MediaType;
import pyxis.uzuki.live.mediaresizer.model.VideoResolutionType;
import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.annotation.OnClick;
import pyxis.uzuki.live.pyxinjector.base.InjectActivity;
import pyxis.uzuki.live.richutilskt.utils.RPickMedia;
import pyxis.uzuki.live.richutilskt.utils.RichUtils;

/**
 * MediaResizer
 * Class: JavaActivity
 * Created by Pyxis on 2017-11-23.
 * <p>
 * Description:
 */

public class JavaActivity extends InjectActivity {
    private @BindView TextView txtStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    @OnClick(R.id.btnCamera)
    private void clickCamera() {
        RPickMedia.instance.pickFromCamera(this, (code, path) -> {
            resultProcess(code, path, MediaType.IMAGE);
        });
    }

    @OnClick(R.id.btnVideo)
    private void clickVideo() {
        RPickMedia.instance.pickFromVideoCamera(this, (code, path) -> {
            resultProcess(code, path, MediaType.VIDEO);
        });
    }

    @OnClick(R.id.btnGallery)
    private void clickGallery() {
        RPickMedia.instance.pickFromGallery(this, (code, path) -> {
            resultProcess(code, path, MediaType.IMAGE);
        });
    }

    @OnClick(R.id.btnVideoGallery)
    private void clickVideoGallery() {
        RPickMedia.instance.pickFromVideo(this, (code, path) -> {
            resultProcess(code, path, MediaType.VIDEO);
        });
    }

    private void resultProcess(int code, String path, MediaType type) {
        if (code == RPickMedia.PICK_FAILED)
            return;

        String realPath = RichUtils.getRealPath(Uri.parse(path), this);

        if (type == MediaType.VIDEO) {
            processVideo(realPath);
        } else {
            processImage(realPath);
        }
    }

    private void processImage(String path) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MediaResizer/",
                RichUtils.asDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + ".jpg");
        file.mkdir();
        DialogInterface progress = RichUtils.progress(this, "Encoding...");

        ResizeOption option = new ResizeOption.Builder()
                .setActivity(this)
                .setImageResolution(1280, 720)
                .setMediaType(MediaType.IMAGE)
                .setTargetPath(path)
                .setOutputPath(file.getAbsolutePath())
                .setCallback((code, output) -> {
                    txtStatus.setText(ResultBuilder.displayImageResult(code, path, output));
                    progress.dismiss();
                }).build();

        MediaResizer.process(option);
    }

    private void processVideo(String path) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MediaResizer/",
                RichUtils.asDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + ".mp4");
        file.mkdir();
        DialogInterface progress = RichUtils.progress(this, "Encoding...");

        ResizeOption option = new ResizeOption.Builder()
                .setActivity(this)
                .setVideoResolutionType(VideoResolutionType.P480)
                .setMediaType(MediaType.VIDEO)
                .setTargetPath(path)
                .setOutputPath(file.getAbsolutePath())
                .setCallback((code, output) -> {
                    txtStatus.setText(ResultBuilder.displayVideoResult(code, path, output));
                    progress.dismiss();
                }).build();

        MediaResizer.process(option);
    }
}
