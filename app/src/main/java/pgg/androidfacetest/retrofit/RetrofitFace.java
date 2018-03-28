package pgg.androidfacetest.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by PDD on 2018/2/28.
 */

public class RetrofitFace extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load("http://baidu.com").into(new ImageView(this));
    }

    private void retrofitHttpGet() throws Exception{
        Retrofit retrofit=new Retrofit.Builder().baseUrl("")
                .build();

        Api api=retrofit.create(Api.class);
        Call<ResponseBody> responseBodyCall = api.contributorBySimpleGetCall("owner", "repo");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("返回的数据是：",response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
