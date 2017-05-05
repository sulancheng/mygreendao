package com.susu.hh.mygreendao.entity;

import com.susu.hh.mygreendao.greendao.CustomerDao;
import com.susu.hh.mygreendao.greendao.DaoSession;
import com.susu.hh.mygreendao.greendao.OrderDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by Administrator
 * on 2017/1/13.
 */

    @Entity
    public class Order {
        @Id
        private Long id;

        private long customerId;

        @ToOne(joinProperty = "customerId")
        private Customer customer;

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
        @Generated(hash = 625323961)
        public void setCustomer(@NotNull Customer customer) {
            if (customer == null) {
                throw new DaoException(
                        "To-one property 'customerId' has not-null constraint; cannot set to-one to null");
            }
            synchronized (this) {
                this.customer = customer;
                customerId = customer.getId();
                customer__resolvedKey = customerId;
            }
        }

        /** To-one relationship, resolved on first access. */
        @Generated(hash = 941511332)
        public Customer getCustomer() {
            long __key = this.customerId;
            if (customer__resolvedKey == null || !customer__resolvedKey.equals(__key)) {
                final DaoSession daoSession = this.daoSession;
                if (daoSession == null) {
                    throw new DaoException("Entity is detached from DAO context");
                }
                CustomerDao targetDao = daoSession.getCustomerDao();
                Customer customerNew = targetDao.load(__key);
                synchronized (this) {
                    customer = customerNew;
                    customer__resolvedKey = __key;
                }
            }
            return customer;
        }

        @Generated(hash = 8592637)
        private transient Long customer__resolvedKey;

        /** called by internal mechanisms, do not call yourself. */
        @Generated(hash = 965731666)
        public void __setDaoSession(DaoSession daoSession) {
            this.daoSession = daoSession;
            myDao = daoSession != null ? daoSession.getOrderDao() : null;
        }

        /** Used for active entity operations. */
        @Generated(hash = 949219203)
        private transient OrderDao myDao;

        /** Used to resolve relations */
        @Generated(hash = 2040040024)
        private transient DaoSession daoSession;

        public long getCustomerId() {
            return this.customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
        }

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Generated(hash = 1580440720)
        public Order(Long id, long customerId) {
            this.id = id;
            this.customerId = customerId;
        }

        @Generated(hash = 1105174599)
        public Order() {
        }
    }


