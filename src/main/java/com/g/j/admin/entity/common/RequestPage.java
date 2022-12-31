package com.g.j.admin.entity.common;

import lombok.Data;

import java.util.Objects;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class RequestPage {
    private Integer page = 1;
    private Integer pageSize = 15;

    private Boolean bitPage = true;

    public <T> BasePage<T> getPageObj() {
        BasePage<T> page = new BasePage<>(getPage(), getLimit());
        if (Objects.equals(false, bitPage)) {
            page.setSize(10000);
        }
        return page;
    }

    public void setBitPage(Boolean bitPage) {
        this.bitPage = bitPage;
        if (!bitPage) {
            pageSize = 10000;
        }
    }

    public Integer getLimit() {
        if (pageSize > 100 && bitPage) {
            pageSize = 100;
        }
        return pageSize;
    }
}
