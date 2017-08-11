package com.susu.hh.mygreendao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.susu.hh.mygreendao.entity.NoteBook;
import com.susu.hh.mygreendao.entity.User;
import com.susu.hh.mygreendao.greendao.NoteBookDao;
import com.susu.hh.mygreendao.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class GreenDaoTest extends Activity {

    private UserDao userDao;
    private NoteBookDao noteBookDao;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao_test);
        DBManager dbManagerInstance = DBManager.getDBManagerInstance(this);
        userDao = dbManagerInstance.getUserDao();
        noteBookDao = dbManagerInstance.getNoteBookDao();

    }
    public void inserts(View view){
        user = new User(null,"28","小黑");
        userDao.insert(user);
    }
    public void inserts2(View view){
        NoteBook haha = new NoteBook(null,"9:00",0);
        haha.setUser(user);
        noteBookDao.insert(haha);
    }
    public void chaxu(View view){
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        userQueryBuilder.where(UserDao.Properties.Age.eq(28)).orderAsc(UserDao.Properties.Age);
        List<User> list = userQueryBuilder.list();
        Log.i("chaxu",list.size()+"===="+list.get(list.size()-1));
        List<NoteBook> list1 = noteBookDao.queryBuilder()
                .where(NoteBookDao.Properties.UserID.eq(list.get(list.size()-1).getId()))
             //   .and(NoteBookDao.Properties.UserID.between())
                .orderAsc(NoteBookDao.Properties.UserID)
                .list();
       Log.i("chaxu",list1.size()+"===="+list1.get(0));
        noteBookDao.queryRaw("");
//        noteBookDao.queryRawCreate("");

    }

//    删
//
//    deleteBykey(Long key) ：根据主键删除一条记录。
//    delete(User entity) ：根据实体类删除一条记录，一般结合查询方法，查询出一条记录之后删除。
//    deleteAll()： 删除所有记录。
//改
//
//update(User entity)：更新一条记录
//查
//
//loadAll()：查询所有记录
//    load(Long key)：根据主键查询一条记录
//    queryBuilder().list()：返回：List
//    queryBuilder().where(UserDao.Properties.Name.eq("")).list()：返回：List
//    queryRaw(String where,String selectionArg)：返回：List

    public void copyu(View view){
        String dbname = "sutest_db";
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
}
