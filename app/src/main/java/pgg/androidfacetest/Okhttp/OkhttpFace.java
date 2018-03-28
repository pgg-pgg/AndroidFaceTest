package pgg.androidfacetest.Okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PDD on 2018/2/28.
 */

public class OkhttpFace extends Activity {

    //第一步：创建一个okHttpClient
    private final OkHttpClient client=new OkHttpClient();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void okhttpAsyRequest() throws Exception{
        //第二步：创建一个Request，通过Builder模式生成
        Request request=new Request.Builder().url("http://www.baidu.com").build();

        //第三步，通过client的call方法创建一个call对象,代表实际的http请求
        Call call = client.newCall(request);

        //同步获取数据
        Response response = call.execute();

        //获取请求头
        Headers headers=response.headers();

        Log.e("请求数据为",response.body().toString());
    }


    private void okhttpSynRequest(){
        //第二步：创建一个Request，通过Builder模式生成
        Request request=new Request.Builder().url("http://www.baidu.com").build();

        //第三步，通过client的call方法创建一个call对象,代表实际的http请求
        Call call = client.newCall(request);

        //异步获取数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取请求头
                Headers headers=response.headers();

                Log.e("请求数据为",response.body().toString());
            }
        });
    }
}
