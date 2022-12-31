package com.g.j.admin.service.common.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements IBaseService<T> {

    /**
     * 树形结构
     * @param dataList 源数据List
     * @param pid 父级主键的名称
     * @param parentId 顶级主键 值
     * @param id 本级主键的名称
     * @param treeName 子级名称
     * @param clazz 转换对象
     * @param depth 最大递归深度
     * @return 树形结构数据
     * @param <R> 泛型
     * @param <t> 泛型
     */
    protected  <R, t> List<t> getChildListTree(List<R> dataList, String pid, Object parentId, String id, String treeName, Class<t> clazz, Integer depth) {
        depth--;
        List<t> arrList = new ArrayList<>();
        if (depth < 0) {
            return arrList;
        }
        for (R data : dataList) {
            Map<String, Object> beanMap = BeanUtil.beanToMap(data);
            if (Objects.equals(String.valueOf(parentId), String.valueOf(beanMap.get(pid)))) {
                beanMap.put(treeName, getChildListTree(dataList, pid, beanMap.get(id), id, treeName, clazz, depth));
                t toBean = BeanUtil.mapToBean(beanMap, clazz, true, CopyOptions.create());
                arrList.add(toBean);
            }
        }
        return arrList;
    }
}
