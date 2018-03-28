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
 *
 * 解决交错的滑动冲突
 *
 * 外部拦截法
 */

public class HorizontalEx extends ViewGroup {

    private boolean isFirstTouch = true;

    private int mLastXIntercept;
    private int mLastYIntercept;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int childCount;
    private int lastX;
    private int lastY;
    private int childIndex;

    public HorizontalEx(Context context) {
        super(context);
        init();
    }


    public HorizontalEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller=new Scroller(getContext());
        mVelocityTracker=VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        childCount = getChildCount();
        //测量子View
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        if (childCount==0){
            setMeasuredDimension(0,0);
        }else if (widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            width=childCount*getChildAt(0).getMeasuredWidth();
            height=getChildAt(0).getHeight();
            setMeasuredDimension(width,height);
        }else if (widthMode==MeasureSpec.AT_MOST){
            width=childCount*getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(width,height);
        }else {
            height=getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width,height);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted=false;
        int x= (int) ev.getX();
        int y= (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastXIntercept=x;
                mLastYIntercept=y;
                intercepted=false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX=x-mLastXIntercept;
                final int deltaY=y-mLastXIntercept;
                if (Math.abs(deltaX)>Math.abs(deltaY)){
                    intercepted=true;
                }else {
                    intercepted=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted=false;
                break;
            default:
                break;
        }
        mLastXIntercept =x;
        mLastYIntercept =y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
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
                    lastX =x;
                    lastY =y;
                    isFirstTouch=false;
                }
                final int deltaX = x - lastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                final int width = getChildAt(0).getWidth();
                mVelocityTracker.computeCurrentVelocity(1000,configuration.getScaledMaximumFlingVelocity());
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity)>configuration.getScaledMinimumFlingVelocity()){
                    childIndex=xVelocity>0?childIndex-1:childCount+1;
                }else {
                    childIndex=(scrollX+width/2)/width;
                }
                childIndex = Math.min(getChildCount() - 1, Math.max(childIndex, 0));
                smoothScrollBy(childIndex * width - scrollX, 0);
                mVelocityTracker.clear();
                isFirstTouch = true;
                break;
        }
        lastX=x;
        lastY=y;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(),getScrollY(),dx,dy,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left=0;
        for (int i=0;i<childCount;i++){
            final View childAt = getChildAt(i);
            childAt.layout(left+l,t,r+left,b);
            left+=childAt.getMeasuredWidth();
        }
    }
}
