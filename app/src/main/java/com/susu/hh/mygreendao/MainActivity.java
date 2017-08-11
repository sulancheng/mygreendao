package com.susu.hh.mygreendao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.susu.hh.mygreendao.entity.User;
import com.susu.hh.mygreendao.greendao.DaoSession;
import com.susu.hh.mygreendao.greendao.UserDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {

    private UserDao userDao;
    private User feng;
    private DaoSession daoSession;
    private EditText savetest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savetest = (EditText) findViewById(R.id.savetest);

//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "myfirst-db", null);
//        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
//
//        //如果是一个成熟的数据库复制进来的话。例如 电话的地理位置的。从外界复制一个db到某个地址，将地址填入第一个。
//        SQLiteDatabase db = SQLiteDatabase.openDatabase("xx/xx/xxx.db", null, SQLiteDatabase.OPEN_READONLY);
////
//        DaoMaster daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//        userDao = daoSession.getUserDao();
    }

    public void insert(View view) {
        feng = new User(null, "28", "feng");
        userDao.insertOrReplace(feng);
       // Intent i = new Intent(this,GreenDaoTest.class);
//        Intent i = new Intent(this,TablyoutActivity.class);
//        startActivity(i);
    }

    public void insertreplace(View view) {
        //同一个对象只是保存一次  根据id
        userDao.insertOrReplace(feng);


        //greendao用sql语句
//        String sql = "select * from " + userDao.getTablename();
      //  Cursor c = daoSession.getDatabase().rawQuery(sql, null);
//        while(c!=null && c.moveToNext()){
//
//        }
//        daoSession.getDatabase().execSQL();
    }

    public void save(View view){
        String trim = savetest.getText().toString().trim();
        File path = Environment.getExternalStorageDirectory();
        String savepath = path.getAbsoluteFile() + "/mysave/";
        File file3 = new File(savepath);
       // File sd = new File(savepath);
        if (!file3.exists()) {
            boolean mkdirs = file3.mkdirs();
            Log.i("fileText",mkdirs+" == "+ file3.exists());
        }
        try
        {
            // 创建文件对象
            File fileText = new File(savepath + "saved.txt");
            Log.i("fileText",fileText.exists()+"");
            // 向文件写入对象写入信息
            FileOutputStream fileWriter = new FileOutputStream(fileText,true);
            // 写文件
            fileWriter.write(trim.getBytes());
            // 关闭
            fileWriter.close();
        }
        catch (IOException e)
        {
            //
            e.printStackTrace();
        }
    }
    public void copy(View view) {
        String dbname = "myfirst-db";
        File databasePath = getDatabasePath(dbname);
        Log.i("databasePath", databasePath.exists() + " da = " + databasePath.getAbsolutePath() + " canwrite = "
                + databasePath.canWrite());
        // /data/data/com.susu.hh.mygreendao/databases/myfirst-db
        File path = Environment.getExternalStorageDirectory();
        String sdcardPath = path.getPath() + "/" + "lenovo/db/";
        Log.i("sdcardPath", sdcardPath);
        File sd = new File(sdcardPath);
        if (!sd.exists()) {
            sd.mkdirs();
        }
        File file = getFilesDir();
        String path3 = file.getAbsolutePath();
        File file3 = new File(path + "/hah.txt");
        Log.i("MainActivityopen", file3.getPath() + "====" + path3);///storage/emulated/0hah.txt====/data/data/com.susu.hh.mygreendao/files

        try {
            openFileInput("hah.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream input = null;
        OutputStream out = null;
        try {
            input = new FileInputStream(databasePath);
            out = new FileOutputStream(sdcardPath + dbname);
            //out = new FileOutputStream("F:\ProgramFiles\Administrator\AndroidStudioProjects\Mygreendao\app\myfirst-db");
            //out = this.openFileOutput("hah.db", Context.MODE_PRIVATE);//keyi
            //操作流,
            int len;
            //一次读一个字节数组 len可以表长度
            byte[] bts = new byte[1024];
            while ((len = input.read(bts)) != -1) {
                out.write(bts, 0, len);
            }
            Log.i("databasePath", "复制成功");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /*
    * Context context=MainActivity.this;//首先，在Activity里获取context
    File file=context.getFilesDir();
    String path=file.getAbsolutePath();
    System.out.println(path);
    File file = new File(path+ceshi.txt);
    FileInputStream inStream = new FileInputStream(file);//需传路径： /data/data/cn.itheima.rw_file/fi


    相当于context.openFileInput("ceshi.txt"）;
    * */
}
