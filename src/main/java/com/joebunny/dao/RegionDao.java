package com.joebunny.dao;

import org.springframework.stereotype.Repository;

import com.joebunny.entity.Region;

@Repository("regionDao")
public class RegionDao extends BaseDaoHibernate<Region> {

}