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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.joebunny.common.BaseBean;

@SuppressWarnings("serial")
@Entity
@Table(name="MENU")
@Cache(region="entity", usage=CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends BaseBean {
    
    @Id @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;
    
    @Column(name="TEXT", nullable=false, length=32)
    private String text;
    
    @Column(name="URL", nullable=false, length=200)
    private String url;
    
    @Column(name="ICONCLS", length=32)
    private String iconCls;
    
    @Column(name="SEQ")
    private Integer seq;
    
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="PID")
    private Menu parent;
    
    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Menu> children = new HashSet<Menu>(0);
    
    public Menu() {
        super();
    }
    public Menu(Integer id, String text, String url, String iconCls, Integer seq, Menu parent, Set<Menu> children) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.iconCls = iconCls;
        this.seq = seq;
        this.parent = parent;
        this.children = children;
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

    public String getIconCls() {
        return iconCls;
    }
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Menu getParent() {
        return parent;
    }
    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public Set<Menu> getChildren() {
        return children;
    }
    public void setChildren(Set<Menu> children) {
        this.children = children;
    }
    
}