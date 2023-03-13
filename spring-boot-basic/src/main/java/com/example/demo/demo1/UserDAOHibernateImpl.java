package com.example.demo.demo1;

public class UserDAOHibernateImpl implements UserDAO {

    @Override
    public void save() {
        System.out.println("UserDAOHibernateImpl执行了...");
    }

}
