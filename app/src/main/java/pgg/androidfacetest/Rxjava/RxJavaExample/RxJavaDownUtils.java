package pgg.androidfacetest.Rxjava.RxJavaExample;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by PDD on 2018/3/15.
 */

public class RxJavaDownUtils {

    private static  OkHttpClient client;
    public RxJavaDownUtils(){
        client=new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).writeTimeout(10,TimeUnit.SECONDS).build();
    }

    public Observable<byte[]> downloadImage(final String path){
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(path).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                byte[] data = response.body().bytes();
                                if (data != null) {
                                    subscriber.onNext(data);
                                }
                                subscriber.onCompleted();
                            }
                        }
                    });
                }
            }
        });
    }
}
