package com.example.mytest.greendao.gen;

import pgg.androidfacetest.greendao.utils.MyApplication;

/**
 * Created by PDD on 2018/3/5.
 */

public class DaoManager {
    private static DaoManager mInstance;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DaoManager(){
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(MyApplication.getContext(),"my_db",null);
        daoMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession=daoMaster.newSession();
    }
    public DaoMaster getDaoMaster(){
        return daoMaster;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public static DaoManager getmInstance(){
        if (mInstance==null){
            mInstance=new DaoManager();
        }
        return mInstance;
    }

}
