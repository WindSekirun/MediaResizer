package pyxis.uzuki.live.mediaresizer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import net.ypresto.androidtranscoder.MediaTranscoder;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import pyxis.uzuki.live.richutilskt.utils.RichUtils;

/**
 * VideoTranscodingUtils
 * Created by Pyxis on 2017-07-11.
 */

public class VideoTranscodingUtils implements MediaTranscoder.Listener {
    private Activity activity;
    private ProgressDialog progressDialog;
    private String incomePath;
    private String outcomePath;
    private OnResultListener listener;

    public static final int TRANSCODING_SUCCESS = 1;
    public static final int TRANSCODING_FAILED = 2;

    public VideoTranscodingUtils(Activity activity, String path) {
        this.activity = activity;
        this.incomePath = path;
        File folder = new File(Environment.getExternalStorageDirectory(), "resizer/encoding_output/");
        folder.mkdir();

        File file = new File(folder, RichUtils.asDateString(System.currentTimeMillis(), "yyyyMMddHHmmss") + ".mp4");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.outcomePath = file.getAbsolutePath();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressDialog = new ProgressDialog(activity, android.R.style.Theme_Material_Light_Dialog);
        } else {
            progressDialog = new ProgressDialog(activity, android.R.style.Theme_Holo_Light_Dialog);
        }

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("동영상 인코딩중...");
        progressDialog.setCancelable(false);
    }

    public void transcode(final OnResultListener listener) {
        this.listener = listener;
        FileDescriptor descriptor = getFileDescriptor();

        MediaTranscoder.getInstance().transcodeVideo(descriptor, outcomePath,
                new Android720pFormatStrategy(1000 * 1000, 128 * 1000, 1), this);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });

    }

    private FileDescriptor getFileDescriptor() {
        File file = new File(incomePath);
        try {
            FileInputStream stream = new FileInputStream(file);
            return stream.getFD();
        } catch (IOException e) {
            return null;
        }
    }

    private void dismissDialog() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (activity.isDestroyed() && !progressDialog.isShowing())
                        return;
                    progressDialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onTranscodeProgress(final double progress) {
    }

    @Override
    public void onTranscodeCompleted() {
        dismissDialog();
        RichUtils.requestMediaScanner(activity, outcomePath);
        listener.onResult(TRANSCODING_SUCCESS, outcomePath);
    }

    @Override
    public void onTranscodeCanceled() {
        dismissDialog();
        listener.onResult(TRANSCODING_FAILED, incomePath);
    }

    @Override
    public void onTranscodeFailed(Exception exception) {
        dismissDialog();
        listener.onResult(TRANSCODING_FAILED, incomePath);
    }

    public interface OnResultListener {
        void onResult(int resultCode, String outPath);
    }
}