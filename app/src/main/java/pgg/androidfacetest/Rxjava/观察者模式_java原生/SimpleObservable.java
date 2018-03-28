package pgg.androidfacetest.Rxjava.观察者模式_java原生;

import java.util.Observable;

/**
 * 创建一个被观察者
 * Created by PDD on 2018/3/15.
 */

public class SimpleObservable extends Observable {

    private int data=0;
    public int getData(){
        return data;
    }
    public void setData(int data){
        if (this.data!=data){
            this.data=data;
            setChanged();//发生改变
            notifyObservers();//表示状态发生改变
        }
    }

}
