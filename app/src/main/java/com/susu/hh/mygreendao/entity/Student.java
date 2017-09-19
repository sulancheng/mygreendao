package com.susu.hh.mygreendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

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
 @Entity(
 schema = "myschema",

 active = true,

 nameInDb = "AWESOME_USERS",

 indexes = {
 @Index(value = "name DESC", unique = true)
 },

 createInDb = false
 )
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
    @Property(nameInDb = "RUXUEDATE")
    Long ruxueDate;
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
    @Generated(hash = 716514068)
    public Student(@NotNull String name, Long stuId, @NotNull String tall,
            @NotNull String age, Long ruxueDate) {
        this.name = name;
        this.stuId = stuId;
        this.tall = tall;
        this.age = age;
        this.ruxueDate = ruxueDate;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", stuId=" + stuId +
                ", tall='" + tall + '\'' +
                ", age='" + age + '\'' +
                ", ruxueDate=" + ruxueDate +
                '}';
    }
    public void setRuxueDate(Long ruxueDate) {
        this.ruxueDate = ruxueDate;
    }
    public Long getRuxueDate() {
        return this.ruxueDate;
    }
}
