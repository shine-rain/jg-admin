package com.g.j.admin.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.g.j.admin.annotation.DataPermission;
import com.g.j.admin.constant.Config;
import com.g.j.admin.constant.Const;
import com.g.j.admin.enums.DataAuthTypeEnum;
import com.g.j.admin.service.system.dept.IJgDeptService;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public class DataAuthInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // 是否需要数据权限
        try {
            if (isDataAuthFilter(ms)) {
                // 读取查询sql字符串形式
                String buildSql = boundSql.getSql();
                String alias = getTableAlias(ms);
                // 获取权限sql
                String sql = authSql(alias);
                if (StringUtils.hasText(sql)) {
                    // 拼接条件
                    String newSql = concatSql(buildSql, sql);
                    PluginUtils.mpBoundSql(boundSql).sql(newSql);
                }
            }
        } catch (ClassNotFoundException ignored) {

        }
    }

    private String authSql(String tableAlias) {
        String sql = "";
        // 获取当前用户主键
        String userId = String.valueOf(StpUtil.getLoginId());
        // 获取权限
        IJgDeptService iJgDeptService = ApplicationContextHolder.getBean(IJgDeptService.class);
        Map<String, Object> deptCode = iJgDeptService.getDeptAuthCodesByUserId(userId);
        // 拼接sql
        if (deptCode.containsKey("flag")) {
            if (DataAuthTypeEnum.ALL.getAuthType().equals(String.valueOf(deptCode.get("flag")))) {
                return sql;
            }
            if (DataAuthTypeEnum.SELF.getAuthType().equals(String.valueOf(deptCode.get("flag")))) {
                sql = "create_user_id="+userId;
                if (StringUtils.hasText(tableAlias)) {
                    sql = tableAlias + "." + sql;
                }
                return sql;
            }
        }
        if (deptCode.containsKey("userIds")) {
            sql = "create_user_id in (" + deptCode.get("userIds") + ")";
            if (StringUtils.hasText(tableAlias)) {
                sql = tableAlias + "." + sql;
            }
        }
        return sql;
    }

    // 判断是否需要数据权限过滤
    private Boolean isDataAuthFilter(MappedStatement mappedStatement) throws ClassNotFoundException {
        if (Config.BIT_DATA_AUTH) {
            String id = mappedStatement.getId();
            // 执行的类
            String className = id.substring(0, id.lastIndexOf("."));
            // 调用的方法
            String methodName = id.substring(id.lastIndexOf(".") + 1);
            final Class<?> cls = Class.forName(className);
            final Method[] method = cls.getMethods();
            // 确认是否需要数据权限过滤
            for (Method m : method) {
                if (m.getName().equals(methodName) && m.isAnnotationPresent(DataPermission.class)) {
                    return !StpUtil.hasRole(Const.SUPER_ADMIN);
                }
            }
        }
        return false;
    }

    private String getTableAlias(MappedStatement mappedStatement) throws ClassNotFoundException {
        String id = mappedStatement.getId();
        // 执行的类
        String className = id.substring(0, id.lastIndexOf("."));
        // 调用的方法
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        final Class<?> cls = Class.forName(className);
        final Method[] method = cls.getMethods();
        // 确认是否需要数据权限过滤
        for (Method m : method) {
            if (m.getName().equals(methodName) && m.isAnnotationPresent(DataPermission.class)) {
                DataPermission dataPermission = m.getAnnotation(DataPermission.class);
                return dataPermission.value();
            }
        }
        return "";
    }

    // 拼接Where条件
    private String concatSql(String originalSql, String conditionSql) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(originalSql);
            SelectBody selectBody = select.getSelectBody();
            // 数据权限拼接sql
            final Expression expressionCondition = CCJSqlParserUtil.parseCondExpression(conditionSql);
            // 合并where条件
            if (selectBody instanceof PlainSelect) {
                PlainSelect plainSelect = (PlainSelect)selectBody;
                final Expression expression = plainSelect.getWhere();
                if (expression == null) {
                    plainSelect.setWhere(expressionCondition);
                } else {
                    AndExpression andExpression = new AndExpression(expression, expressionCondition);
                    plainSelect.setWhere(andExpression);
                }
                // 返回sql
                return plainSelect.toString();
            }
        } catch (Exception ignored) {

        }
        return originalSql;
    }

}
