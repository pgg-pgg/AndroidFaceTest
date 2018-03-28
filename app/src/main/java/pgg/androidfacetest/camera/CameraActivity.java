package pgg.androidfacetest.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.FileOutputStream;

import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/28.
 */

public class CameraActivity extends Activity {
    public static final int CAMERA_RESULT = 1234;
    public static final int REQUEST_PICKER_ALBUM = 2345;
    private static String TAG = "CameraObserver";
    public static String photopath = "";
    ImageView imageView;
    Button buttonExit;
    Button buttonCapture;
    private Camera mCamera;
    private CameraViewNew mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // CameraObserver已经将photopath路径传过来了
        Log.d(TAG, "CameraActivity onCreate photopath = " + photopath);
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.capture_activity);
        mCamera = getCameraInstance();
        // 创建预览类，并与Camera关联，最后添加到界面布局中

        mPreview = new CameraViewNew(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置横屏模式以及全屏模式
        // setContentView(new CameraView(this,photopath)); //设置View
        buttonCapture = (Button) findViewById(R.id.button_capture);
        imageView=findViewById(R.id.iv_person);
        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        // TODO Auto-generated method stub
                        mCamera.takePicture(null, null, myPicture);

                    }
                });
            }

        });
        buttonExit=(Button) findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    /** 打开一个Camera */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(1);
        } catch (Exception e) {
            Log.d(TAG, "打开Camera失败失败");
        }
        return c;
    }

    private Camera.PictureCallback myPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.d(TAG, " onPictureTaken = " + photopath);
            BitmapFactory.Options options=new BitmapFactory.Options();

            try {
                    options.inSampleSize=2;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                    imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
            }
            camera.stopPreview();

        }

        private void data2file(byte[] w, String fileName) throws Exception {// 将二进制数据转换为文件的函数
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(fileName);
                out.write(w);
                out.close();
            } catch (Exception e) {
                if (out != null)
                    out.close();
                throw e;
            }
        }
    };
}
