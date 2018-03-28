package pgg.androidfacetest.内存泄露;

import android.app.AppOpsManager;
import android.content.Context;

/**
 * Created by PDD on 2018/3/5.
 */

public class SingleClass {

    //有内存泄露的问题的单例
//    private static SingleClass mInstance;
//    private Context context;
//    private SingleClass(Context context){
//        this.context=context;
//    }
//
//    public static SingleClass getmInstance(Context context){
//        if (mInstance==null){
//            mInstance=new SingleClass(context);
//        }
//        return mInstance;
//    }

    //修复内存泄漏的写法
    private static SingleClass mInstance;
    private Context context;
    private SingleClass(Context context){
        this.context=context.getApplicationContext();
    }

    public static SingleClass getmInstance(Context context){
        if (mInstance==null){
            mInstance=new SingleClass(context);
        }
        return mInstance;
    }

}
