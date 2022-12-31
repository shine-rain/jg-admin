package com.g.j.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.g.j.admin.enums.UploadFileEnum;
import com.g.j.admin.service.common.upload.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Configuration
public class ConfigBean {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor innerInterceptor = new MybatisPlusInterceptor();
        innerInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        innerInterceptor.addInnerInterceptor(new DataAuthInterceptor());
        return innerInterceptor;
    }

    @Bean
    public static IFileService iFileService() {
        UploadConfig uploadConfig = ApplicationContextHolder.getBean(UploadConfig.class);
        String type = uploadConfig.getType();
        if (type.equals(UploadFileEnum.ALI_OSS.getType())) {
            return new OssFileServiceImpl(uploadConfig.getOss());
        } else if (type.equals(UploadFileEnum.LOCAL.getType())) {
            return new LocalFileServiceImpl(uploadConfig.getLocal());
        }else if(type.equals(UploadFileEnum.TENCENT_COS.getType())){
            return new TencentFileServiceImpl(uploadConfig.getCos());
        }else if(type.equals(UploadFileEnum.QN_QNC.getType())){
            return new QncFileServiceImpl(uploadConfig.getQnc());
        } else if(type.equals(UploadFileEnum.MINIO.getType())) {
            return new MinioFileServiceImpl(uploadConfig.getMinio());
        }
        return new LocalFileServiceImpl(uploadConfig.getLocal());
    }
}
