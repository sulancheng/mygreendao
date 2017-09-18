package com.susu.hh.mygreendao.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.susu.hh.mygreendao.greendao.DaoMaster;
import com.susu.hh.mygreendao.greendao.NoteBookDao;
import com.susu.hh.mygreendao.upgraputils.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * Created by su
 * on 2017/5/19.df
 * 如果只是改变build中的配置则会删除表中的所有历史数据
 */
public class MySQLiteOpenHelper extends DaoMaster.DevOpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, NoteBookDao.class);
    }
}