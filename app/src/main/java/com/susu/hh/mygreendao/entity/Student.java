package com.susu.hh.mygreendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sucheng
 * on 2017/7/31.
 *  * // 对一，实体属性 joinProperty 对应外联实体ID
 @ToOne(joinProperty = "fk_dogId")
 private Dog dog;

 // 对多。实体ID对应外联实体属性 referencedJoinProperty
 @ToMany(referencedJoinProperty = "fk_userId")
 private List<Cat> cats;

 // 对多。@JoinProperty：name 实体属性对应外联实体属性 referencedName
 @ToMany(joinProperties = {
 @JoinProperty(name = "horseName", referencedName = "name")
 })
 */
@Entity
public class Student {
    // 非空
    @NotNull
    String name;
    @Id
    Long stuId;
    // 非空
    @NotNull
    String tall;
    // 非空
    @NotNull
    String age;
//    @ToMany(joinProperties = {
//            @JoinProperty(name = "name", referencedName = "name")
//    })
//    User user;
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getTall() {
        return this.tall;
    }
    public void setTall(String tall) {
        this.tall = tall;
    }
    public Long getStuId() {
        return this.stuId;
    }
    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Generated(hash = 143096220)
    public Student(@NotNull String name, Long stuId, @NotNull String tall,
            @NotNull String age) {
        this.name = name;
        this.stuId = stuId;
        this.tall = tall;
        this.age = age;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
}
