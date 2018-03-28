package pgg.androidfacetest.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by PDD on 2018/2/28.
 */

public class VolleyFace extends Activity {

    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Volley第一步：获取RequestQueue对象
        queue = Volley.newRequestQueue(this);


    }

    private void VolleyStringRequest(){

        //volley第二步，创建新的Request
        StringRequest request=new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("返回成功的数据",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("返回失败的原因",error.getMessage(),error);
            }
        });

        //volley第三步，将request添加到RequestQueue中
        queue.add(request);
    }
}
