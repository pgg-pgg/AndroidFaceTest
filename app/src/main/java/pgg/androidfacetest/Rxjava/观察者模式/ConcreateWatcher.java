package pgg.androidfacetest.Rxjava.观察者模式;

/**
 * Created by PDD on 2018/3/15.
 */

public class ConcreateWatcher implements Watcher {

    @Override
    public void update(String str) {
        System.out.println(str);
    }
}
