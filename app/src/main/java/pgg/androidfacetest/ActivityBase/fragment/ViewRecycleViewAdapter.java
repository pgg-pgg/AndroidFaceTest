package pgg.androidfacetest.ActivityBase.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/18.
 */

public class ViewRecycleViewAdapter extends RecyclerView.Adapter<ViewRecycleViewAdapter.ViewHolder> {

    private static final int TAG_POS_KEY = 213<<24;
    public int[] colors = {Color.BLUE, Color.GREEN, Color.YELLOW, Color.GRAY};
    private List<Integer> heights;
    private List<ViewBean> data;
    private Context context;
    private LayoutInflater inflater;

    private OnItemClickListener mOnItemClickListener;

    interface OnItemClickListener {
        void onItemClick(int pos, View view);
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ViewRecycleViewAdapter(Context context,List<ViewBean> data) {
        this.context=context;
        this.data=data;
        inflater=LayoutInflater.from(context);
        generateHeights();
    }

    private void generateHeights() {
        if (data == null) return;
        heights = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int height = new Random().nextInt(100) + 200;
            heights.add(height);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_view_fragments,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v.getTag(TAG_POS_KEY)==null?0:(int)v.getTag(TAG_POS_KEY),v);
                }
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.view_recycleView_item_tv.setText(data.get(position).getContent());
        holder.view_recycleView_item_tv.setBackgroundColor(colors[position%colors.length]);
        ViewGroup.LayoutParams layoutParams = holder.view_recycleView_item_tv.getLayoutParams();
        layoutParams.height=heights.get(position);
        holder.view_recycleView_item_tv.setLayoutParams(layoutParams);
        holder.itemView.setTag(TAG_POS_KEY,position);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public Object getItem( int pos){
        return data.get(pos) ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.view_recycleView_item_tv)
        TextView view_recycleView_item_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
