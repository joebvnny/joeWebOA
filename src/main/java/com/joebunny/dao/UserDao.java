package com.joebunny.dao;

import org.springframework.stereotype.Repository;

import com.joebunny.entity.User;

@Repository("userDao")
public class UserDao extends BaseDaoHibernate<User> {

}