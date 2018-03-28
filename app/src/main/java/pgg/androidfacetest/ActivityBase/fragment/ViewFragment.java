package pgg.androidfacetest.ActivityBase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.ActivityBase.activity.ScrollConflictActivity;
import pgg.androidfacetest.ActivityBase.activity.StickNavActivity;
import pgg.androidfacetest.ActivityBase.base.BaseFragment;
import pgg.androidfacetest.ActivityBase.widget.SpaceItemDecoration;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/18.
 */

public class ViewFragment extends BaseFragment {

    @Bind(R.id.fg_view_recycleview)
    RecyclerView fg_view_recycleview;
    private ViewRecycleViewAdapter adapter;

    public static BaseFragment newInstance(){
        Bundle bundle=new Bundle();
        ViewFragment fragment=new ViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_view,container,false);
        ButterKnife.bind(this,root);
        initView();
        return root;
    }

    private void initView() {
        fg_view_recycleview.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        fg_view_recycleview.addItemDecoration(new SpaceItemDecoration(16,2));

        List<ViewBean> datas = new ArrayList<>();
        datas.add(new ViewBean(1,"滑动冲突"));
        datas.add(new ViewBean(2,"事件分发机制"));
        datas.add(new ViewBean(3,"StickNavLayout")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;
        datas.add(new ViewBean(3,"未实现")) ;

        adapter = new ViewRecycleViewAdapter(getActivity(),datas);
        adapter.setmOnItemClickListener(onItemClickListener);
        fg_view_recycleview.setAdapter(adapter);
    }

    private ViewRecycleViewAdapter.OnItemClickListener onItemClickListener = new ViewRecycleViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int pos, View view) {
            ViewBean viewBean = (ViewBean) adapter.getItem(pos);
            switch (viewBean.getId()) {
                case 0:break;
                case 1:
                    //滑动冲突
                    Intent intent =new Intent(getContext(), ScrollConflictActivity.class);
                    startActivity(intent);
                    break;
                case 2:

                    break;
                case 3:
                    Intent intent1 = new Intent(getContext(), StickNavActivity.class);
                    break;
            }
        }
    };
}
