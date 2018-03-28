package pgg.androidfacetest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import pgg.androidfacetest.AsyncTask.AsyncTaskFace;
import pgg.androidfacetest.BroadcastFace.BroadReceiverTest;
import pgg.androidfacetest.ServiceFace.ServiceTestBind;
import pgg.androidfacetest.ServiceFace.ServiceTestStart;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_start, btn_bind, btn_stop, btn_unbind;
    Button btn_register, btn_unregister;
    private Connection conn;
    BroadReceiverTest receiverTest;
    TextView textView;
    ProgressBar progressBar;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //....
        }
    };


    class Connection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceTestBind.A a = (ServiceTestBind.A) service;
            ServiceTestBind serviceTest = a.getServiceTest();
            serviceTest.getString();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("dsadasdadadad", "服务断开连接");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_bind = (Button) findViewById(R.id.btn_bind);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_unbind = (Button) findViewById(R.id.btn_unbind);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_unregister = (Button) findViewById(R.id.btn_unregister);

        btn_start.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_unregister.setOnClickListener(this);
        conn = new Connection();
        textView = (TextView) findViewById(R.id.tv_show);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        AsyncTaskFace asyncTaskFace = new AsyncTaskFace(textView, progressBar);
        asyncTaskFace.execute(2017);

        Bundle bundle = new Bundle();
    }

    @Override
    protected void onResume() {
        receiverTest = new BroadReceiverTest();
        IntentFilter filter = new IntentFilter("com.pgg.haha");
        registerReceiver(receiverTest, filter);
        super.onResume();

    }


//    @Override
//    protected void onDestroy() {
//        if (receiverTest!=null){
//            unregisterReceiver(receiverTest);
//        }
//        super.onDestroy();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startService(new Intent(MainActivity.this, ServiceTestStart.class));
                break;
            case R.id.btn_stop:
                stopService(new Intent(MainActivity.this, ServiceTestStart.class));
                break;
            case R.id.btn_bind:
                Intent intent = new Intent(MainActivity.this, ServiceTestBind.class);
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(conn);
                break;
            case R.id.btn_register:
                Intent intent1 = new Intent();
                intent1.setAction("com.pgg.haha");
                intent1.putExtra("name", "彭港归");
                this.sendBroadcast(intent1);
                break;
            case R.id.btn_unregister:
                unregisterReceiver(receiverTest);
                break;
        }
    }

    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) reference.get();
            if (activity != null) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
