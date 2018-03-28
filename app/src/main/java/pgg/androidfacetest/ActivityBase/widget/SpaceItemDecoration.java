package pgg.androidfacetest.ActivityBase.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PDD on 2018/3/18.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space,num;

    public SpaceItemDecoration(int space,int num) {
        this.space=space;
        this.num=num;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.bottom=space;
        outRect.right=space;
        if (parent.getChildAdapterPosition(view)<num){
            outRect.top=space;
        }
    }
}
