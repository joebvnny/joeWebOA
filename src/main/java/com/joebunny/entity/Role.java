package com.joebunny.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.joebunny.common.BaseBean;

@SuppressWarnings("serial")
@Entity
@Table(name="ROLE")
@Cache(region="entity", usage=CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseBean {
    
    @Id @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;
    
    @Column(name="ROLENAME", nullable=false, length=32)
    private String rolename;
    
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="ROLE_FUNC", joinColumns={@JoinColumn(name="role_id")}, inverseJoinColumns={@JoinColumn(name="func_id")})
    private Set<Func> funcs = new HashSet<Func>(0);
    
    @ManyToMany(mappedBy="roles", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<User> users = new HashSet<User>(0);
    
    public Role() {
        super();
    }
    public Role(Integer id, String rolename, Set<Func> funcs, Set<User> users) {
        this.id = id;
        this.rolename = rolename;
        this.funcs = funcs;
        this.users = users;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    
    public Set<Func> getFuncs() {
        return funcs;
    }
    public void setFuncs(Set<Func> funcs) {
        this.funcs = funcs;
    }
    
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
}