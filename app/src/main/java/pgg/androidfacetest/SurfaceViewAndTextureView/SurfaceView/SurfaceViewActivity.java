package pgg.androidfacetest.SurfaceViewAndTextureView.SurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/8.
 */

public class SurfaceViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SurfaceViewText(this));
    }


}
