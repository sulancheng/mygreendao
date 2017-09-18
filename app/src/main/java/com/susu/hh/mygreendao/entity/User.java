package com.susu.hh.mygreendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Administrator
 * on 2017/1/7.
 */
@Entity
public class User {
    @Id
    private Long id;
    @Index(unique = true)
    private String age;
    @Property(nameInDb = "NAME")
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1829406839)
    public User(Long id, String age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age='" + age + '\'' +
                ", name='" + name + '\'' +
                ", tempUsageCount=" + tempUsageCount +
                '}';
    }
}
