package com.joebunny.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joebunny.common.BaseBean;

@SuppressWarnings("serial")
@Entity
@Table(name="REGION")
@Cache(region="entity", usage=CacheConcurrencyStrategy.READ_WRITE)
public class Region extends BaseBean {

    @Id @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;
    
    @Column(name="Name", nullable=false, length=50)
    private String name;
    
    @Column(name="Level")
    private Integer level;
    
    @Column(name="ParentId")
    private Integer parentId;
    
    @Column(name="CenterLng", precision=10, scale=6)
    private Double centerLng;
    
    @Column(name="CenterLat", precision=10, scale=6)
    private Double centerLat;
    
    @Column(name="ZipCode", length=6)
    private String zipCode;
    
    @Column(name="CityCode", length=6)
    private String cityCode;
    
    @Column(name="AreaCode", length=12)
    private String areaCode;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name="CreatedTime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    
    public Region() {
        super();
    }
    public Region(Integer id, String name, Integer level, Integer parentId, Double centerLng, Double centerLat, String zipCode, String cityCode, String areaCode, Date createdTime) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parentId = parentId;
        this.centerLng = centerLng;
        this.centerLat = centerLat;
        this.zipCode = zipCode;
        this.cityCode = cityCode;
        this.areaCode = areaCode;
        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public Double getCenterLng() {
        return centerLng;
    }
    public void setCenterLng(Double centerLng) {
        this.centerLng = centerLng;
    }

    public Double getCenterLat() {
        return centerLat;
    }
    public void setCenterLat(Double centerLat) {
        this.centerLat = centerLat;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}