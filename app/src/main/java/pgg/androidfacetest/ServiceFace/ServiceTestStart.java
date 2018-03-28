package pgg.androidfacetest.ServiceFace;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by PDD on 2018/2/27.
 */

public class ServiceTestStart extends Service {
    /**
     * 普通开启方式
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"服务被创建",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"服务开启",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("dddddddddddddddddddd","服务被销毁");
        Toast.makeText(this,"服务被销毁",Toast.LENGTH_SHORT).show();
    }
}
