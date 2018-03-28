package pgg.androidfacetest.Rxjava.RxJavaTest;

import android.nfc.Tag;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PDD on 2018/3/15.
 */

public class RxJavaTest {

    private static final String TAG = RxJavaTest.class.getSimpleName();

    /**
     * Create的第一种方式
     */
    public static void CreateObservable() {
        //定义被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("hello");
                    subscriber.onNext("hi");
                    subscriber.onNext(downLoad());
                    subscriber.onNext("World");
                    subscriber.onCompleted();
                }
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "result--->" + s);
            }
        };
        //将观察者与被观察者关联
        observable.subscribe(subscriber);
    }

    private static String downLoad() {
        return "DownLoad Success";
    }

    /**
     * Create 的第二种方式
     */
    public static void CreatePrint() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "result->>>" + integer);
            }
        });
    }

    /**
     * from 操作，数据类型一般是数组
     */
    public static void from() {
        Integer[] items = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Observable observable = Observable.from(items);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.e(TAG, o.toString());
            }
        });
    }

    /**
     * interval 定时去发送数据
     */
    public static void interval() {
        Integer[] items = new Integer[]{1, 2, 3, 4, 5};
        Observable observable = Observable.interval(1, 1, TimeUnit.SECONDS);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.e(TAG, o.toString());
            }
        });
    }


    /**
     * just 处理数据集合
     */
    public static void just() {
        Integer[] items = new Integer[]{1, 2, 3, 4, 5, 6};
        Integer[] ite = new Integer[]{2, 3, 4, 5, 6, 7};
        Observable observable = Observable.just(items, ite);
        observable.subscribe(new Subscriber<Integer[]>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onNext(Integer[] o) {
                for (int i=0;i<o.length;i++){
                    Log.e(TAG,o[i]+"");
                }
            }
        });
    }


    /**
     * range 指定输出数据的范围
     */
    public static void range(){
        Observable observable=Observable.range(1,40);
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG,"next:"+o);
            }
        });
    }


    /**
     * filter 过滤功能
     */
    public static void filter(){
        Observable observable=Observable.just(1,2,3,4,5,6);
        observable.filter(new Func1<Integer,Boolean>() {
            @Override
            public Boolean call(Integer o) {
                return o<5;
            }
        }).observeOn(Schedulers.io())
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer o) {
                Log.e(TAG,"next:"+o.toString());
            }
        });
    }
}
