package com.joebunny.dao;

import org.springframework.stereotype.Repository;

import com.joebunny.entity.dto.UserInfo;

@Repository("userInfoCache")
public class UserInfoCache extends CacheDaoJedis<UserInfo> {

}