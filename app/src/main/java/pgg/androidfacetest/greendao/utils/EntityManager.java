package pgg.androidfacetest.greendao.utils;

import com.example.mytest.greendao.gen.DaoManager;
import com.example.mytest.greendao.gen.UserDao;

/**
 * Created by PDD on 2018/3/5.
 */

public class EntityManager {
    private static EntityManager manager;
    private UserDao userDao;

    public UserDao getUserDao(){
        return DaoManager.getmInstance().getDaoSession().getUserDao();
    }

    public static EntityManager getManager(){
        if (manager==null){
            manager=new EntityManager();
        }
        return manager;
    }
}
