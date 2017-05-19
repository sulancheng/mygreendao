package com.susu.hh.mygreendao.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.susu.hh.mygreendao.greendao.DaoMaster;

/**
 * Created by su
 * on 2017/5/19.
 */
public class MySQLiteOpenHelper extends DaoMaster.DevOpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}