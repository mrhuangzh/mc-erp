package com.mc.erp.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: mrhuangzh
 * @date: 2024/7/6 8:52
 **/
@Configuration
public class MybatisPlusConfig {

    /**
     * 添加分页插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(false);// 溢出总页数后是否进行处理
        paginationInnerInterceptor.setMaxLimit(1000L);// 单页分页条数限制
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        return interceptor;
    }
}
