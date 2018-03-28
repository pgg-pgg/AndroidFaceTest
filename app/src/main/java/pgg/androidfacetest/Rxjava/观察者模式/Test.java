package pgg.androidfacetest.Rxjava.观察者模式;

/**
 * Created by PDD on 2018/3/15.
 */

public class Test {

    public static void main(String[] args){
        Watched xiaotou= new ConcreateWatched();

        Watcher watcher1=new ConcreateWatcher();
        Watcher watcher2=new ConcreateWatcher();
        Watcher watcher3=new ConcreateWatcher();
        Watcher watcher4=new ConcreateWatcher();

        xiaotou.addWatcher(watcher1);
        xiaotou.addWatcher(watcher2);
        xiaotou.addWatcher(watcher3);
        xiaotou.addWatcher(watcher4);

        xiaotou.notifyWatcher("我要偷东西了");
    }
}
