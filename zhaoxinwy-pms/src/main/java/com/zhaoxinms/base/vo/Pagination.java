package com.zhaoxinms.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data 
public class Pagination extends Page{
    private long pageSize=20;
    private long maxPageSize = 50;
    private String sort="desc";
    private String sidx="";
    private long currentPage=1;

    @JsonIgnore
    private long total;
    @JsonIgnore
    private long records;
    @JsonIgnore
    private long offset;

    public <T> List<T> setData(List<T> data, long records) {
        this.total = records;
        return data;
    }
    
    public void setPageSize(long size) {
    	if(size>maxPageSize) {
    		this.pageSize = maxPageSize;
    	}else {
    		this.pageSize = size;
    	}
    }
    
    public long getOffset() {
    	if(pageSize*(currentPage-1)>0) {
    		return pageSize*(currentPage-1);
    	}else {
    		return 0;
    	}
    }
}
