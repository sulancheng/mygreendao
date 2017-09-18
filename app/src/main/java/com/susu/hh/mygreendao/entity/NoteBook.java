package com.susu.hh.mygreendao.entity;

import com.susu.hh.mygreendao.greendao.DaoSession;
import com.susu.hh.mygreendao.greendao.NoteBookDao;
import com.susu.hh.mygreendao.greendao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * 这里的意思是 userId作为外键与User的主键（也就是id）相连。
 * on 2017/1/12.
 * // 对一，实体属性 joinProperty 对应外联实体ID
 @ToOne(joinProperty = "fk_dogId")
 private Dog dog;

 // 对多。实体ID对应外联实体属性 referencedJoinProperty
 @ToMany(referencedJoinProperty = "fk_userId")
 private List<Cat> cats;

 // 对多。@JoinProperty：name 实体属性对应外联实体属性 referencedName
 @ToMany(joinProperties = {
 @JoinProperty(name = "horseName", referencedName = "name")
 })

 *
 *
 */
@Entity
public class NoteBook {
    @Id
    private Long id;
    private String time;
    private String name;
    private Long userID;
    @ToOne(joinProperty = "userID")
    private User user;
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1078392129)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userID = user == null ? null : user.getId();
            user__resolvedKey = userID;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1725002132)
    public User getUser() {
        Long __key = this.userID;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1888691330)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteBookDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 1692630944)
    private transient NoteBookDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public long getUserID() {
        return this.userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 806213997)
    public NoteBook(Long id, String time, String name, Long userID) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.userID = userID;
    }
    @Generated(hash = 2066935268)
    public NoteBook() {
    }

    @Override
    public String toString() {
        return "NoteBook{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", userID=" + userID +
                ", user=" + user +
                ", user__resolvedKey=" + user__resolvedKey +
                ", myDao=" + myDao +
                ", daoSession=" + daoSession +
                '}';
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
