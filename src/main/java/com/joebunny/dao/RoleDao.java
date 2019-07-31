package com.joebunny.dao;

import org.springframework.stereotype.Repository;

import com.joebunny.entity.Role;

@Repository("roleDao")
public class RoleDao extends BaseDaoHibernate<Role> {

}