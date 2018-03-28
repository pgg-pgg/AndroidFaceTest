package pgg.androidfacetest.greendao.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by PDD on 2018/3/5.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
