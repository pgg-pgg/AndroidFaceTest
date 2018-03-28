package pgg.androidfacetest.Rxjava.观察者模式_java原生;

/**
 * Created by PDD on 2018/3/15.
 */

public class Test {

    public static void main(String[] args){
        SimpleObservable simpleObservable=new SimpleObservable();

        SimpleObserver observer=new SimpleObserver(simpleObservable);

        simpleObservable.setData(1);
        simpleObservable.setData(2);
        simpleObservable.setData(2);
        simpleObservable.setData(3);

    }
}
