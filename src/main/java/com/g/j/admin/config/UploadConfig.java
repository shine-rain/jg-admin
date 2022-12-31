package com.g.j.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class UploadConfig {

    /**
     * 上传类型 本地 阿里云 腾讯云
     */
    private String type;

    /**
     * oss配置
     */
    private OssConfig oss;

    /**
     * 腾讯云配置
     */
    private CosConfig cos;

    /**
     * 七牛云配置
     */
    private QncConfig qnc;

    /**
     * 本地配置
     */
    private LocalConfig local;

    /**
     * minio
     */
    private MinIoConfig minio;

    @Data
    public static class OssConfig {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private Map<String,String> bucketName;
        private String domainUrl;

    }

    @Data
    public static class CosConfig{
        private String region;
        private String secretId;
        private String secretKey;
        private Map<String,String> bucketName;
        private String domainUrl;
    }

    @Data
    public static class QncConfig{
        private String accessKey;
        private String secretKey;
        private Map<String,String> bucketName;
        private String domainUrl;
    }

    @Data
    public static class MinIoConfig{
        private String accessKey;
        private String secretKey;
        private Map<String,String> bucketName;
        private String domainUrl;
    }

    @Data
    public static class LocalConfig {
        private String uploadPath;
        private String domainUrl;
    }

}
