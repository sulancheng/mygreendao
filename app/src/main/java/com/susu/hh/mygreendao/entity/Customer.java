package com.susu.hh.mygreendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator
 * on 2017/1/13.
 */
    @Entity
    public class Customer {
        @Id
        private Long id;

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Generated(hash = 470110355)
        public Customer(Long id) {
            this.id = id;
        }

        @Generated(hash = 60841032)
        public Customer() {
        }
    }

