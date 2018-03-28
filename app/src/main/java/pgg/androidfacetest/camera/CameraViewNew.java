package pgg.androidfacetest.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pgg.androidfacetest.Utils;

/**
 * Created by PDD on 2018/3/28.
 */

public class CameraViewNew extends SurfaceView implements SurfaceHolder.Callback {


    private final static String TAG="CameraView";
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private Context context;
    private Camera.AutoFocusCallback autoFocusCallback=null;
    IOrientationEventListener orientationEventListener;
    public CameraViewNew(Context context, Camera camera) {
        super(context);
        this.context=context;
        mCamera=camera;
        mHolder=getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //当SurfaceView尺寸变化时（包括设备横屏竖屏改变时时），需要重新设定相关参数
        if (mHolder.getSurface() == null) {
            //检查SurfaceView是否存在
            return;
        }
        //改变设置前先关闭相机
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用最佳比例配置重启相机
        try {
            mCamera.setPreviewDisplay(mHolder);
            final Camera.Parameters parameters = mCamera.getParameters();
            final Camera.Size size = getBestPreviewSize(width, height);
            parameters.setPreviewSize(size.width, size.height);
            setCameraDisplayOrientation((Activity)context,1,mCamera);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            onFocus(new Point(250, 250), new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success){
                        Log.e(TAG,"聚焦成功");
                    }
                }
            });
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    private Camera.Size getBestPreviewSize(int width, int height) {
        Camera.Size result = null;
        final Camera.Parameters p = mCamera.getParameters();
        //特别注意此处需要规定rate的比是大的比小的，不然有可能出现rate = height/width，但是后面遍历的时候，current_rate = width/height,所以我们限定都为大的比小的。
        float rate = (float) Math.max(width, height)/ (float)Math.min(width, height);
        float tmp_diff;
        float min_diff = -1f;
        for (Camera.Size size : p.getSupportedPreviewSizes()) {
            float current_rate = (float) Math.max(size.width, size.height)/ (float)Math.min(size.width, size.height);
            tmp_diff = Math.abs(current_rate-rate);
            if( min_diff < 0){
                min_diff = tmp_diff ;
                result = size;
            }
            if( tmp_diff < min_diff ){
                min_diff = tmp_diff ;
                result = size;
            }
        }
        return result;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            orientationEventListener=new IOrientationEventListener(context);
            orientationEventListener.enable();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera =null;
    }

    private void setCameraDisplayOrientation(Activity activity,int cameraId,Camera camera){
        Camera.CameraInfo info=new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId,info);
        int rotation=activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees=0;
        switch (rotation){
            case Surface.ROTATION_0: degrees=0;break;
            case Surface.ROTATION_90: degrees=90; break;
            case Surface.ROTATION_180:degrees=180; break;
            case Surface.ROTATION_270:degrees=270; break;
        }
        int result;
        if (info.facing==Camera.CameraInfo.CAMERA_FACING_FRONT){
            result=(info.orientation+degrees)%360;
            result=(360-result)%360;
        }else {
            result=(info.orientation-degrees+360)%360;
        }
        camera.setDisplayOrientation(result);
    }


    private Rect calculateTapArea(float x, float y, float coefficient, Camera.Size previewSize) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerY =0;
        int  centerX=0;
        centerY = (int) (x / Utils.getScreenWidth(getContext())*2000 - 1000);
        centerX= (int) (y / Utils.getScreenHeight(getContext())*2000 - 1000);
        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);

        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }


    protected void onFocus(Point point, Camera.AutoFocusCallback callback){
        Camera.Parameters parameters=mCamera.getParameters();
        if (parameters.getMaxNumFocusAreas()<=0) {
            mCamera.autoFocus(callback);
            return;
        }
        mCamera.cancelAutoFocus();
        List<Camera.Area> areas=new ArrayList<Camera.Area>();
        List<Camera.Area> areasMetrix=new ArrayList<Camera.Area>();
        Camera.Size previewSize = parameters.getPreviewSize();
        Rect focusRect = calculateTapArea(point.x, point.y, 1.0f, previewSize);
        Rect metrixRect = calculateTapArea(point.x, point.y, 1.5f, previewSize);
        areas.add(new Camera.Area(focusRect, 1000));
        areasMetrix.add(new Camera.Area(metrixRect,1000));
        parameters.setMeteringAreas(areasMetrix);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setFocusAreas(areas);
        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        mCamera.autoFocus(callback);
    }
    private static int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    public class IOrientationEventListener extends OrientationEventListener {

        public IOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (ORIENTATION_UNKNOWN == orientation) {
                return;
            }
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(1, info);
            orientation = (orientation + 45) / 90 * 90;
            int rotation = 0;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                rotation = (info.orientation - orientation + 360) % 360;
            } else {
                rotation = (info.orientation + orientation) % 360;
            }
//            Log.e("TAG","orientation: " + orientation);
            if (null != mCamera) {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setRotation(rotation);
                mCamera.setParameters(parameters);
            }
        }
    }
}
