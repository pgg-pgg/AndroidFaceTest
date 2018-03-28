package pgg.androidfacetest.SurfaceViewAndTextureView.TextureView;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Permission;
import java.security.Permissions;
import java.util.jar.Manifest;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.MPermissionUtils;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/8.
 */

public class TextureViewActivity extends Activity implements TextureView.SurfaceTextureListener {

    private MediaPlayer mediaPlayer;

    @Bind(R.id.texture)
    TextureView textureView;
    @Bind(R.id.video_image)
    ImageView video_image;
    private Surface surface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        ButterKnife.bind(this);
        textureView.setSurfaceTextureListener(this);//设置监听，实现四个方法
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        System.out.println("onSurfaceTextureAvailable被执行");
        surface = new Surface(surfaceTexture);
        new PlayerVideo().start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        System.out.println("onSurfaceTextureSizeChanged被执行");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        System.out.println("onSurfaceTextureDestroyed被执行");
        surfaceTexture=null;
        surface=null;
        mediaPlayer.stop();
        mediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    class PlayerVideo extends Thread{
        @Override
        public void run() {
            MPermissionUtils.requestPermissionsResult(TextureViewActivity.this, 1, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    File file=new File(Environment.getExternalStorageDirectory()+"/tv.mp4");
                    if (!file.exists()){
                        copyFile();
                    }
                    mediaPlayer=new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(file.getAbsolutePath());
                        mediaPlayer.setSurface(surface);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                video_image.setVisibility(View.GONE);
                                mediaPlayer.start();
                            }
                        });
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onPermissionDenied() {
                    MPermissionUtils.showTipsDialog(TextureViewActivity.this);
                }
            });

        }
    }

    /**
     * 如果sdcard没有文件就复制过去
     */
    private void copyFile() {
        AssetManager assetManager = this.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("tv.mp4");
            String newFileName = Environment.getExternalStorageDirectory()+"/tv.mp4";
            out = new FileOutputStream(newFileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
