package pgg.androidfacetest.Rxjava.RxJavaExample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pgg.androidfacetest.R;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PDD on 2018/3/15.
 */

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.image)
    ImageView imageView;
    @Bind(R.id.btn_down)
    Button btn_dowm;

    RxJavaDownUtils utils;
    private static final String PATH="http://f.hiphotos.baidu.com/image/h%3D300/sign=12e703ffa5ec8a130b1a51e0c7029157/c75c10385343fbf2f7da8133bc7eca8065388f2f.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        ButterKnife.bind(this);
        utils=new RxJavaDownUtils();
    }

    @OnClick(R.id.btn_down)
    public void onDownImage(){
        Observable<byte[]> observable = utils.downloadImage(PATH);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<byte[]>() {
            @Override
            public void onCompleted() {
                //在收到数据完成后可以做一些类似关闭加载框的操作
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
