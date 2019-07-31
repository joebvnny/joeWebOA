package com.joebunny.dao;

import org.springframework.stereotype.Repository;

import com.joebunny.entity.Dept;

@Repository("deptDao")
public class DeptDao extends BaseDaoHibernate<Dept> {

}