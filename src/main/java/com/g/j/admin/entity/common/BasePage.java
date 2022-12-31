package com.g.j.admin.entity.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public class BasePage<T> implements IPage<T> {

    /**
     * 查询数据列表
     */
    private List<T> list = new ArrayList<>();
    /**
     * 查询数据列表
     */
    private List<T> rows = new ArrayList<>();
    /**
     * 总数
     */
    private long total = 0;

    /**
     * 每页显示条数，默认 15
     */
    private long pageSize = 15;

    /**
     * 排序字段信息
     */
    private final List<OrderItem> orders = new ArrayList<>();

    /**
     * 是否进行 count 查询
     */
    private boolean searchCount = true;

    /**
     * 当前页
     */
    private long page = 1;

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public BasePage() {

    }

    public BasePage(long current, long pageSize) {
        if (current > 1) {
            this.page = current;
        }
        this.pageSize = pageSize;
    }

    @Override
    @JsonIgnore
    public List<T> getRecords() {
        return this.list;
    }
    @JsonIgnore
    public List<T> getList() {
        return this.list;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    @JsonIgnore
    public long getPageNumber() {
        return this.page;
    }

    public boolean isFirstPage() {
        return this.page == 1L;
    }

    public boolean isLastPage() {
        return getTotal() == 0 || this.page == getPages();
    }

    public void setPageNumber(long page) {
        this.page = page;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public BasePage<T> setRecords(List<T> records) {
        this.list = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public BasePage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    @JsonIgnore
    public long getSize() {
        return this.pageSize;
    }

    @Override
    public BasePage<T> setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.page;
    }

    @Override
    public BasePage<T> setCurrent(long current) {
        this.page = current;
        return this;
    }

    /**
     * 添加新的排序条件
     * @param items 条件
     * @return 返回分页参数本身
     */
    public BasePage<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }
    @Override
    @JsonIgnore
    public boolean searchCount() {
        if (total < 0) {
            return false;
        }
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
