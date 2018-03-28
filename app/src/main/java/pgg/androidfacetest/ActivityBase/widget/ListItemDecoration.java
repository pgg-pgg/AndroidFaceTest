package pgg.androidfacetest.ActivityBase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PDD on 2018/3/17.
 */

public class ListItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int orientation;
    private final static int DEFALUT_ORIENTATION = LinearLayoutManager.VERTICAL;
    private final Drawable drawable;

    public ListItemDecoration(Context context, int orientation) {
        this.context = context;
        if (orientation != LinearLayoutManager.VERTICAL || orientation != LinearLayoutManager.HORIZONTAL) {
            this.orientation = DEFALUT_ORIENTATION;
        }else {
            this.orientation=orientation;
        }
        final TypedArray array = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        drawable = array.getDrawable(0);
        array.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation==LinearLayoutManager.HORIZONTAL){
            drawHorizontal(c,parent);
        }else if (orientation==LinearLayoutManager.VERTICAL){
            drawVertical(c,parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int top = childAt.getTop() + params.topMargin;
            int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    private void drawHorizontal(Canvas canvas,RecyclerView parent){
        int top=parent.getPaddingTop();
        int bottom=parent.getHeight()-parent.getPaddingBottom();
        int childCount=parent.getChildCount();
        for (int i=0;i<childCount;i++){
            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left=childAt.getLeft()+params.leftMargin;
            int right=left+drawable.getIntrinsicWidth();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
