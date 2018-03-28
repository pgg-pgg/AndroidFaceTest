package pgg.androidfacetest.ActivityBase.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.ActivityBase.home.data.HomeData;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private List<HomeData> mData;

    private LayoutInflater mLayoutInflater ;

    private Context context;
    public HomeAdapter(Context context,List<HomeData> mData) {
        this.context=context;
        this.mData=mData;
        mLayoutInflater=LayoutInflater.from(context);
    }

    private OnItemClickListener listener;
    interface OnItemClickListener{
        void onItemClick(int pos,int id);
    }
    public void setListener(OnItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_list_home, null);
        return new HomeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        holder.tv_content.setText(mData.get(position).getContent());
        holder.image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position,mData.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.image_view)
        ImageView image_view;
        @Bind(R.id.tv_content)
        TextView tv_content;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
