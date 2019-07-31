package com.joebunny.entity.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页和排序描述对象
 */
public class Pager {

    private Integer page;       // 当前第几页
    private Integer rows;       // 每页多少行
    private Integer totalRows;  // 总记录行数
    private Integer totalPage;  // 总分页数
    private Integer startRow;   // 起始行标
    private Integer endRow;     // 结束行标
    
    private String sort;            // 排序字段
    private String order = "asc";   // 排序顺序
    
    public Pager() {
        super();
    }
    /**
     * 构建分页对象（不排序）
     * 
     * @param page  当前第几页
     * @param rows  每页多少行
     */
    public Pager(Integer page, Integer rows) {
        if(page==null || page < 1)  page = 1;
        if(rows==null || rows < 1)  rows = 10;
        this.startRow = rows * (page - 1);
        this.endRow = rows * page - 1;
        this.page = page;
        this.rows = rows;
    }
    /**
     * 构建排序对象（不分页）
     * 
     * @param sort  排序字段
     * @param order 排序顺序（asc/desc）
     */
    public Pager(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }
    /**
     * 构建分页且排序对象
     */
    public Pager(Integer page, Integer rows, String sort, String order) {
        this(page, rows);
        this.sort = sort;
        this.order = order;
    }
    
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getRows() {
        return rows;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    
    public Integer getTotalRows() {
        return totalRows;
    }
    /**
     * 设置记录总数（返给前端必须）
     */
    public Pager setTotalRows(Integer totalRows) {
        if(this.page==null || this.page<1)  this.page = 1;
        if(this.rows==null || this.rows<1)  this.rows = 10;
        this.startRow = this.rows * (this.page - 1);
        this.endRow = this.rows * this.page - 1;
        
        if(totalRows!=null && totalRows>0) {
            this.totalRows = totalRows;
            this.totalPage = (this.totalRows + this.rows - 1) / this.rows;
            this.endRow = (this.rows > this.totalRows ? this.totalRows-1 : this.startRow+this.rows-1);
            if(this.page >= this.totalPage) {
                this.page = this.totalPage;
                this.endRow = this.totalRows - 1;
                this.startRow = this.rows * (this.totalPage - 1);
            }
            if(this.rows > this.totalRows) {
                this.rows = this.totalRows;
            }
        }
        
        return this;
    }
    
    public Integer getTotalPage() {
        return totalPage;
    }
    
    public Integer getStartRow() {
        return startRow;
    }
    
    public Integer getEndRow() {
        return endRow;
    }
    
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    
    /**
     * 根据传递的排序参数生成排序SQL后缀
     */
    public String getOrderBySQL() {
        StringBuffer sql = new StringBuffer("");
        if(StringUtils.isEmpty(order)) {
            order = "asc";
        }
        if(StringUtils.isNotEmpty(sort)) {
            String[] sorts = sort.split(",");
            String[] orders = order.split(",");
            
            if(orders.length == 1) {
                for(String sqlSort : sorts) {
                    sql.append(sqlSort + " " + order + ",");
                }
            } else {
                for(int i=0; i<orders.length; i++) {
                    sql.append(sorts[i] + " " + orders[i] + ",");
                }
            }
        }
        String orderBySQL = sql.toString();
        if(StringUtils.isNotEmpty(orderBySQL)) {
            orderBySQL = " ORDER BY " + orderBySQL.substring(0, orderBySQL.lastIndexOf(","));
        }
        return orderBySQL;
    }
    
}