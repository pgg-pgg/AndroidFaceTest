package pgg.androidfacetest.Rxjava.观察者模式;

/**
 * Created by PDD on 2018/3/15.
 */

public interface Watched {

    void addWatcher(Watcher watcher);

    void removeWatcher(Watcher watcher);

    void notifyWatcher(String str);

}
