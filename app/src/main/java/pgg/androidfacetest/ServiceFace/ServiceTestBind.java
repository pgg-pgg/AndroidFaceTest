package pgg.androidfacetest.ServiceFace;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by PDD on 2018/2/27.
 */

public class ServiceTestBind extends Service {

    A a=new A();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return a;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this,"服务被绑定创建",Toast.LENGTH_SHORT).show();
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this,"服务被销毁",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"服务启动",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    public class A extends Binder{
        public ServiceTestBind getServiceTest(){
            return ServiceTestBind.this;
        }
    }

    public void getString(){
        Log.e("服务的方法被调用了","哈哈哈，我被调用了");
    }
}
