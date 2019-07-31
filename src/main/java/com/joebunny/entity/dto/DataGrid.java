package com.joebunny.entity.dto;

import java.util.List;

/**
 * 返给页面[数据表格]组件的数据传输对象
 */
public class DataGrid {

    // 总记录条数
	private Integer total;
	// 数据对象列表
    private List<?> rows;
    
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    
    public DataGrid() {
        super();
    }
    public DataGrid(Integer total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

}