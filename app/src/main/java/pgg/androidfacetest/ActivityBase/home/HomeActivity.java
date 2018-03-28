package pgg.androidfacetest.ActivityBase.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.ActivityBase.base.BaseActivity;
import pgg.androidfacetest.ActivityBase.home.data.HomeData;
import pgg.androidfacetest.ActivityBase.view.PagerActivity;
import pgg.androidfacetest.ActivityBase.widget.ListItemDecoration;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/17.
 */

public class HomeActivity extends BaseActivity {

    private final static String TAG="HomeActivity";

    @Bind(R.id.recycle_home_view)
    RecyclerView recycle_home_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycle_home_view.setLayoutManager(manager);
        recycle_home_view.addItemDecoration(new ListItemDecoration(this,LinearLayoutManager.HORIZONTAL));
        HomeAdapter adapter=new HomeAdapter(this,HomeData.getData());
        recycle_home_view.setAdapter(adapter);
        setListener(adapter);
    }

    private void setListener(HomeAdapter adapter) {
        adapter.setListener(new HomeAdapter.OnItemClickListener() {
            Intent intent=new Intent(HomeActivity.this, PagerActivity.class);
            @Override
            public void onItemClick(int pos, int id) {

                switch (id){
                    case 0:
                        //多线程分析
                        intent.putExtra(PagerActivity.FRAGMENT_ID_INTENT_KEY, id);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra(PagerActivity.FRAGMENT_ID_INTENT_KEY, id);
                        startActivity(intent);
                        break;
                    case 2:

                        break;
                }
            }
        });
    }
}
