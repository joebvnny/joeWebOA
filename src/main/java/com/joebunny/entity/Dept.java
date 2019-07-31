package com.joebunny.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.joebunny.common.BaseBean;

@SuppressWarnings("serial")
@Entity
@Table(name="DEPT")
@Cache(region="entity", usage=CacheConcurrencyStrategy.READ_WRITE)
public class Dept extends BaseBean {
    
    @Id @GeneratedValue
    private Integer id;
    
    @Column(name="DEPTNAME", nullable=false, length=32)
    private String deptname;
    
//    @OneToMany(mappedBy="dept", targetEntity=User.class, fetch=FetchType.LAZY, cascade=CascadeType.ALL)
//    private List<User> users;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

}