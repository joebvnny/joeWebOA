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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.joebunny.common.BaseBean;

@SuppressWarnings("serial")
@Entity
@Table(name="FUNC")
@Cache(region="entity", usage=CacheConcurrencyStrategy.READ_WRITE)
public class Func extends BaseBean {
    
    @Id @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;
    
    @Column(name="TEXT", nullable=false, length=32)
    private String text;
    
    @Column(name="URL", nullable=false, length=200)
    private String url;
    
    @Column(name="SEQ")
    private Integer seq;
    
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="PID")
    private Func parent;
    
    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Func> children = new HashSet<Func>(0);
    
    @ManyToMany(mappedBy="funcs", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Role> roles = new HashSet<Role>(0);
    
    public Func() {
        super();
    }
    public Func(Integer id, String text, String url, Integer seq, Func parent, Set<Func> children, Set<Role> roles) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.seq = seq;
        this.parent = parent;
        this.children = children;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Func getParent() {
        return parent;
    }
    public void setParent(Func parent) {
        this.parent = parent;
    }

    public Set<Func> getChildren() {
        return children;
    }
    public void setChildren(Set<Func> children) {
        this.children = children;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
}