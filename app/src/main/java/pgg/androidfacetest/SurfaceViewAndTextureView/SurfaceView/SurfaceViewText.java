package pgg.androidfacetest.SurfaceViewAndTextureView.SurfaceView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by PDD on 2018/3/8.
 */

public class SurfaceViewText extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder surfaceHolder;
    private boolean isDrawing;
    private Canvas canvas;
    private Paint paint;
    private Path path;

    private float mLastX, mLastY;//上次的坐标
    public static final int TIME_IN_FRAME = 30;

    public SurfaceViewText(Context context) {
        super(context);
        //初始化
        init();
    }

    private void init() {
        surfaceHolder = getHolder();//获取Surface管理对象，SurfaceHolder
        surfaceHolder.addCallback(this);//注册SurfaceHolder
        setFocusable(true);//设置SurfaceView可获取焦点
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);//保持屏幕常亮

        //设置画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);//设置线段连接处样式，圆弧
        paint.setStrokeCap(Paint.Cap.ROUND);//画笔笔刷类型,影响画笔始末端
        //初始化路径
        path = new Path();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            /**取得更新之前的时间**/
            long startTime = System.currentTimeMillis();
            synchronized (surfaceHolder){
                drawUI();
            }
            /**取得更新结束的时间**/
            long endTime = System.currentTimeMillis();

            /**计算出一次更新的毫秒数**/
            int diffTime  = (int)(endTime - startTime);

            /**确保每次更新时间为30帧**/
            while(diffTime <=TIME_IN_FRAME) {
                diffTime = (int)(System.currentTimeMillis() - startTime);
                /**线程等待**/
                Thread.yield();
            }
        }
    }

    private void drawUI() {
        try {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawPath(path, paint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (surfaceHolder!=null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xStart = event.getRawX();
        float yStart = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = xStart;
                mLastY = yStart;
                path.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(xStart - mLastX);
                float dy = Math.abs(yStart - mLastY);
                if (dx >= 3 || dy >= 3) {
                    path.quadTo(mLastX, mLastY, (mLastX + xStart) / 2, (mLastY + yStart) / 2);
                }
                mLastX = xStart;
                mLastY = yStart;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }
}
