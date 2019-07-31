package com.joebunny.dao;

import org.springframework.stereotype.Repository;

import com.joebunny.entity.Menu;

@Repository("menuDao")
public class MenuDao extends BaseDaoHibernate<Menu> {

}