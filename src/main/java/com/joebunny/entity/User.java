package com.joebunny.entity;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.joebunny.common.BaseBean;

@SuppressWarnings("serial")
@Entity
@Table(name="USER")
@Cache(region="entity", usage=CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseBean {
    
    @Id @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;
    
    @Column(name="USERNAME", nullable=false, length=32)
    private String username;
    
    @Column(name="PASSWORD", nullable=false, length=64)
    private String password;
    
    @Column(name="BIRTHDAY")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    
    @Column(name="GENDER", length=1)
    private Character gender;
    
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="USER_ROLE", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="role_id")})
    private Set<Role> roles = new HashSet<Role>(0);
    
    @ManyToOne(targetEntity=Dept.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="deptId")
    private Dept dept;
    
    public User() {
        super();
    }
    public User(Integer id, String username, String password, Date birthday, Character gender, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.roles = roles;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public Character getGender() {
        return gender;
    }
    public void setGender(Character gender) {
        this.gender = gender;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    
}