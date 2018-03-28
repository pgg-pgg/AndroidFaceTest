package pgg.androidfacetest.ActivityBase.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pgg.androidfacetest.ActivityBase.base.BaseActivity;
import pgg.androidfacetest.ActivityBase.fragment.ScrollConflictFragment;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/18.
 */

public class ScrollConflictActivity extends BaseActivity {

//    @Bind(R.id.btn_out_scroll_hv)
//    Button btn_out_scroll_hv;
//
//    @Bind(R.id.btn_out_scroll_vv)
//    Button btn_out_scroll_vv;
//
//    @Bind(R.id.btn_innner_scroll_hv)
//    Button btn_innner_scroll_hv;
//
//    @Bind(R.id.btn_innner_scroll_vv)
//    Button btn_innner_scroll_vv;
//
//    @Bind(R.id.fragment_container)
//    FrameLayout fragment_container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_srcoll_confict);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_out_scroll_hv,R.id.btn_out_scroll_vv,R.id.btn_innner_scroll_hv,R.id.btn_innner_scroll_vv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_out_scroll_hv:
                //外部拦截法——交错
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ScrollConflictFragment.newInstance(ScrollConflictFragment.OUT_HV))
                        .commit();
                break;
            case R.id.btn_out_scroll_vv:
                //外部拦截法——同向
                break;
            case R.id.btn_innner_scroll_hv:
                //内部拦截法——交错
                break;
            case R.id.btn_innner_scroll_vv:
                //内部拦截法——同向

                break;
        }
    }
}
