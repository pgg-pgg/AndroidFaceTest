package pgg.androidfacetest.Rxjava.观察者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDD on 2018/3/15.
 */

public class ConcreateWatched implements Watched {

    private List<Watcher> watcherList=new ArrayList<>();
    @Override
    public void addWatcher(Watcher watcher) {
        watcherList.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        watcherList.remove(watcher);
    }

    @Override
    public void notifyWatcher(String str) {
        for (Watcher watcher:watcherList){
            watcher.update(str);
        }
    }
}
