package pgg.androidfacetest.ActivityBase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by PDD on 2018/3/18.
 * 内部拦截
 */

public class HorizontalEx2 extends ViewGroup {

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int lastX;
    private int lastY;
    private int childIndex;

    public HorizontalEx2(Context context) {
        super(context);
        init();
    }

    public HorizontalEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller=new Scroller(getContext());
        mVelocityTracker=VelocityTracker.obtain();
    }

    private static boolean isFirstTouch=true;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        int y=(int)event.getY();
        mVelocityTracker.addMovement(event);
        ViewConfiguration configuration=ViewConfiguration.get(getContext());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFirstTouch){
                    isFirstTouch=false;
                    lastX =x;
                    lastY =y;
                }
                final int deltaX=x-lastX;
                scrollBy(deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                isFirstTouch=true;
                int scrollX=getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000,configuration.getScaledMaximumFlingVelocity());
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity)>configuration.getScaledMinimumFlingVelocity()){
                    childIndex=xVelocity<0 ? childIndex + 1 : childIndex - 1;
                }else {
                    childIndex=(scrollX+getChildAt(0).getWidth()/2)/getChildAt(0).getWidth();
                }
                Math.min(getChildCount()-1,Math.max(childIndex,0));
                smoothScrollBy(childIndex*getChildAt(0).getWidth()-scrollX,0);
                mVelocityTracker.recycle();
                break;
        }
        lastX=x;
        lastY=y;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        int childCount=getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        if (childCount==0){
            setMeasuredDimension(0,0);
        }else if (widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            width=getChildAt(0).getMeasuredWidth()*childCount;
            height=getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width,height);
        }else if (widthMode==MeasureSpec.AT_MOST){
            width=getChildAt(0).getMeasuredWidth()*childCount;
            setMeasuredDimension(width,height);
        }else {
            height=getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width,height);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        if (action==MotionEvent.ACTION_DOWN){
            if (!mScroller.isFinished()){
                mScroller.abortAnimation();
                return true;
            }
            return false;
        }else {
            return true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftOffets=0;
        for (int i=0;i<getChildCount();i++){
            View childAt = getChildAt(0);
            childAt.layout(l+leftOffets,t,r+leftOffets,b);
            leftOffets+=childAt.getMeasuredWidth();
        }
    }
}
