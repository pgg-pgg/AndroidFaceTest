package pgg.androidfacetest.单例模式;

/**
 * Created by PDD on 2018/3/13.
 */

public class SingleTon {

    private static SingleTon mInstance=new SingleTon();
    private SingleTon(){

    }

    public static SingleTon getmInsatnce(){
        return mInstance;
    }



    public static SingleTon getmInstance(){
        if (mInstance==null){
            synchronized (SingleTon.class){
                if (mInstance==null){
                    mInstance=new SingleTon();
                }
            }
        }
        return mInstance;
    }
}
