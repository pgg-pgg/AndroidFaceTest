package pgg.androidfacetest.ActivityBase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import pgg.androidfacetest.ActivityBase.base.BaseActivity;
import pgg.androidfacetest.ActivityBase.fragment.ThreadFragment;
import pgg.androidfacetest.ActivityBase.fragment.ViewFragment;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/17.
 */

public class PagerActivity extends BaseActivity {

    public static final String FRAGMENT_ID_INTENT_KEY = "fragment_key";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        startFragment();
    }

    private void startFragment() {
        Fragment fragment=null;
        switch (getFragmentId()){
            case 0:
                fragment= ThreadFragment.newInstance();
                break;
            case 1:
                fragment= ViewFragment.newInstance();
                break;
            case 2:

                break;
            case 3:

                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commitAllowingStateLoss();
    }

    private int getFragmentId() {
        return getIntent().getIntExtra(FRAGMENT_ID_INTENT_KEY, 0);
    }
}
