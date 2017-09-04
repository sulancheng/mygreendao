package com.susu.hh.mygreendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.susu.hh.mygreendao.greendao.DaoMaster;
import com.susu.hh.mygreendao.greendao.DaoSession;
import com.susu.hh.mygreendao.greendao.NoteBookDao;
import com.susu.hh.mygreendao.greendao.UserDao;
import com.susu.hh.mygreendao.help.MySQLiteOpenHelper;

/**
 * Created by Administrator
 * on 2017/1/12.
 */
public class DBManager {
    private final static String dbName = "sutest_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DBManager(Context context) {
        this.context = context;
        //openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        openHelper = new MySQLiteOpenHelper(context, dbName, null);
    }
    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getDBManagerInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase db;
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        if(db == null){
            db = openHelper.getWritableDatabase();
        }
        return db;
    }
    public DaoSession getDaoSession(){
        if(daoMaster == null){
            daoMaster = new DaoMaster(getWritableDatabase());
        }
        if(daoSession == null){
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
    public UserDao getUserDao(){
        UserDao userDao = getDaoSession().getUserDao();
        return userDao;
    }
    public DaoSession pubgetDaoSession(){
        return daoSession;
    }
    public NoteBookDao getNoteBookDao(){
        NoteBookDao userDao = getDaoSession().getNoteBookDao();
        return userDao;
    }
}

