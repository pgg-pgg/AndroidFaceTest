package pgg.androidfacetest.IntentService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by PDD on 2018/2/28.
 */

public class IntentServiceActivity extends Activity implements IntentServiceFace.UpdateUI{

    private final static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //更新UI
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=new Intent(this,IntentServiceFace.class);
        intent.putExtra("download_url","图片的Url地址");
        startService(intent);
        IntentServiceFace.setUpdateUI(this);
    }

    @Override
    public void updateUI(Message msg) {
        handler.sendMessageDelayed(msg,1000);
    }
}
