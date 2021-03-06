package com.susu.hh.mygreendao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.susu.hh.mygreendao.entity.NoteBook;
import com.susu.hh.mygreendao.entity.Student;
import com.susu.hh.mygreendao.entity.User;
import com.susu.hh.mygreendao.greendao.DaoSession;
import com.susu.hh.mygreendao.greendao.NoteBookDao;
import com.susu.hh.mygreendao.greendao.StudentDao;
import com.susu.hh.mygreendao.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class GreenDaoTest extends Activity {

    private UserDao userDao;
    private NoteBookDao noteBookDao;
    private DaoSession daoSession;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao_test);
        DBManager dbManagerInstance = DBManager.getDBManagerInstance(this);
        userDao = dbManagerInstance.getUserDao();
        studentDao = dbManagerInstance.getStudentDao();
        noteBookDao = dbManagerInstance.getNoteBookDao();
        daoSession = dbManagerInstance.pubgetDaoSession();
    }

    public void addstu(View view) {
        Student student = new Student();
        student.setName("haha");
        student.setAge("21");
        student.setTall("185");
        Date date = new Date();
        student.setRuxueDate(date.getTime()-10000);
        studentDao.insertOrReplace(student);
    }

    public void selestu(View view) {

        QueryBuilder<Student> sb = studentDao.queryBuilder();
        try {
            List<Student> liststu = sb.list();
            Log.i("daimachaxu", liststu.toString());
            sqlchaxu();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("daimachaxu", "数据库stu为空");
            return;
        }
    }

    private void sqlchaxu() {
        try {
            List<Student> students = studentDao.queryRaw("");
            Log.i("daimachaxu", students.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("daimachaxu", "数据库stu为空");
        }

    }

    public void inserts(View view) {
        User user = new User();
        user.setAge(24 + "");
        user.setName("我爱的你");
        user.setId((long) 5);
        user.setRead("我早就准备好了");
        userDao.insertOrReplace(user);
        NoteBook haha = new NoteBook(null, "12:00", "神墓", user.getId(),"12000");
        haha.setUser(user);
        noteBookDao.insertOrReplace(haha);
    }


    public NoteBook cheakDb(String id) {
        List<NoteBook> noteBooks = noteBookDao.queryRaw("where _id=? order by _id asc", id);
        return noteBooks.get(0);
    }

    public List<NoteBook> cheakDbt(String time, User user) {
        List<NoteBook> noteBooks = noteBookDao.queryRaw("where time=? and USER_ID=? order by _id asc", time, user.getId() + "");
        return noteBooks;
    }

    public void inserts2(View view) {
        User user = new User();
        user.setId((long) 11);
        List<NoteBook> noteBooks = cheakDbt("12:00", user);
        Log.i("dadede", noteBooks.toString());
        daimachaxu("12:00", user);
    }

    public void daimachaxu(String time, User user) {
        QueryBuilder<NoteBook> noteBookQueryBuilder = noteBookDao.queryBuilder();
        List<NoteBook> list = noteBookQueryBuilder
                .where(NoteBookDao.Properties.Time.eq(time), NoteBookDao.Properties.UserID.eq(user.getId() + ""))
                .list();
        Log.i("daimachaxu", list.toString());
    }

    public void chaxu(View view) {
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        userQueryBuilder.where(UserDao.Properties.Age.eq(22)).orderAsc(UserDao.Properties.Age);
        List<User> list = userQueryBuilder.list();
        if (list != null && list.size() != 0) {
            Log.i("chaxu", list.size() + "====" + list.get(list.size() - 1));
            List<NoteBook> list1 = noteBookDao.queryBuilder()
                    .where(NoteBookDao.Properties.UserID.eq(list.get(list.size() - 1).getId()))
                    //   .and(NoteBookDao.Properties.UserID.between())
                    .orderAsc(NoteBookDao.Properties.UserID)
                    .list();
            Log.i("chaxu", list1.size() + "====" + list1.get(0));
        }

        List<NoteBook> noteBooks = noteBookDao.queryRaw("where _id=? order by _id asc", "3");
        Log.i("取出数据的长度", noteBooks.size() + "");
        for (NoteBook mybook : noteBooks) {
            User user = mybook.getUser();
            Log.i("语句取出的数据=", mybook.toString() + "ren" + user);
        }
        List<User> users = userDao.queryRaw("where _id=?", noteBooks.get(0).getUserID() + "");
        Log.i("语句取出的数据=users", users.get(0).toString());
        //List<NoteBook> noteBooks = noteBookDao.queryRaw("order by _id asc","1");
        //noteBookDao.queryRawCreate("");
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

    public void copyu(View view) {
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
