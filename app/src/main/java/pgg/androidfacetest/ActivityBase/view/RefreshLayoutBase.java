package pgg.androidfacetest.ActivityBase.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/19.
 */

public abstract class RefreshLayoutBase<T extends View> extends ViewGroup {


    public static final int STATUS_LOADING = 1;
    public static final int STATUS_RELEASE_TO_REFRESH = 2;
    public static final int STATUS_PULL_TO_REFRESH = 3;
    public static final int STATUS_IDLE = 4;
    public static final int STATUS_LOAD_MORE =5;
    private static int SCROLL_DURATION =500;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mTouchSlop;
    private Scroller mScroller;
    private OnRefreshListener mOnRefreshListener;
    private ViewGroup mHeadView;
    private ProgressBar mProgressBar;
    private TextView mTv;
    private ViewGroup mFootView;
    private ProgressBar mFootProgressBar;
    private TextView mFootTv;
    private int mInitScroolY=0;
    private int mLastXIntercepted;
    private int mLastYIntercepted;
    private int mLastX;
    private int mLastY;
    private boolean isFirstTouch=true;
    private double mCurrentState;

    public RefreshLayoutBase(Context context) {
        super(context, null);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getScreenSize();
        initView();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
        setPadding(0, 0, 0, 0);
    }

    public void setContentView(T view) {
        addView(view, 1);
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    private void initView() {
        setupHeadView();
        setupFootView();
    }

    private int dp2px(int dp) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    private void setupFootView() {
        mFootView = (ViewGroup) View.inflate(getContext(), R.layout.fresh_foot_view, null);
        mFootView.setBackgroundColor(Color.BLUE);
        mFootProgressBar = mFootView.findViewById(R.id.fresh_foot_progressbar);
        mFootTv = mFootView.findViewById(R.id.fresh_foot_tv);
        addView(mFootView);
    }

    private void setupHeadView() {
        mHeadView = (ViewGroup) View.inflate(getContext(), R.layout.fresh_head_view, null);
        mHeadView.setBackgroundColor(Color.RED);
        mProgressBar = mHeadView.findViewById(R.id.head_progressbar);
        mTv = mHeadView.findViewById(R.id.head_tv);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, mScreenHeight / 4);
        mHeadView.setLayoutParams(params);
        mHeadView.setPadding(0, mScreenHeight / 4 - dp2px(100), 0, 0);
        addView(mHeadView);
    }

    private void getScreenSize() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int finalHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            finalHeight += childAt.getMeasuredHeight();
        }
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, finalHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, height);
        } else {
            setMeasuredDimension(widthSize, finalHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topOffsets = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.layout(getPaddingLeft(), topOffsets + getPaddingTop(), r, getPaddingTop() + childAt.getMeasuredHeight() + topOffsets);
            topOffsets += childAt.getMeasuredHeight();
        }
        mInitScroolY = mHeadView.getMeasuredHeight() + getPaddingTop();
        scrollTo(0, mInitScroolY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepter=false;
        int x= (int) ev.getX();
        int y= (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastXIntercepted = x;
                mLastYIntercepted = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final int detalY=y-mLastYIntercepted;
                if (isTop()&&detalY>0&&Math.abs(detalY)>mTouchSlop){
                    /**
                     * 下拉
                     */
                    intercepter=true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        mLastYIntercepted=y;
        mLastXIntercepted=x;
        return intercepter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFirstTouch){
                    mLastX=x;
                    mLastY=y;
                }
                final int deltaY=x-mLastY;
                if (mCurrentState !=STATUS_LOADING){
                    changeScroller(deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                isFirstTouch=true;
                doRefresh();
                break;
        }
        mLastX=x;
        mLastY=y;
        return true;
    }

    private void doRefresh() {
        if (mCurrentState == STATUS_RELEASE_TO_REFRESH) {
            mScroller.startScroll(0, getScrollY(), 0, mInitScroolY - getScrollY(), SCROLL_DURATION);
            mCurrentState = STATUS_IDLE;
        } else if (mCurrentState == STATUS_PULL_TO_REFRESH) {
            mScroller.startScroll(0,getScrollY(),0,0-getScrollY(),SCROLL_DURATION);
            if (null != mOnRefreshListener) {
                mCurrentState = STATUS_LOADING;
                mOnRefreshListener.refresh();
            }
        }
        invalidate();
    }

    private void changeScroller(int deltaY) {
        int curY=getScrollY();
        if (deltaY>0){
            //下拉
            if (curY-deltaY>getPaddingTop()){
                scrollBy(0,-deltaY);
            }
        }else {
            if (curY-deltaY<mInitScroolY){
                scrollBy(0,-deltaY);
            }
        }

        curY = getScrollY();
        int slop = mInitScroolY / 2;
        if (curY > 0 && curY <=slop) {
            mCurrentState = STATUS_PULL_TO_REFRESH;
        } else if (curY > 0 && curY >= slop) {
            mCurrentState = STATUS_RELEASE_TO_REFRESH;
        }
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 加载完成调用这个方法
     */
    public void refreshComplete() {
        mScroller.startScroll(0, getScrollY(), 0, mInitScroolY - getScrollY(), SCROLL_DURATION);
        mCurrentState = STATUS_IDLE;
        invalidate();
    }

    /**
     * 显示 Footer
     */
    public void showFooter() {
        if(mCurrentState==STATUS_LOAD_MORE) return ;
        mCurrentState = STATUS_LOAD_MORE ;
        mScroller.startScroll(0, getScrollY(), 0, mFootView.getMeasuredHeight(), SCROLL_DURATION);
        invalidate();
    }

    /**
     * loadMore完成之后调用
     */
    public void footerComplete() {
        mScroller.startScroll(0, getScrollY(), 0, mInitScroolY - getScrollY(), SCROLL_DURATION);
        invalidate();
        mCurrentState = STATUS_IDLE;
    }

    public interface OnRefreshListener {
        void refresh();
    }

    abstract boolean isTop();

    abstract boolean isBottom();
}
