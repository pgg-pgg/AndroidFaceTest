package pgg.androidfacetest.Rxjava.观察者模式_java原生;

import java.util.Observable;
import java.util.Observer;

/**
 * 创建一个观察者
 * Created by PDD on 2018/3/15.
 */

public class SimpleObserver implements Observer {

    public SimpleObserver(SimpleObservable observable){
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("data is changed"+((SimpleObservable)o).getData());
    }
}
