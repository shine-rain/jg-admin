package com.g.j.admin.enums;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public enum UploadFileEnum {

    /**
     * 本地上传
     */
    LOCAL("local","本地存储"),
    /**
     * 阿里云OSS
     */
    ALI_OSS("ali","阿里云存储"),
    /**
     * 腾讯云COS
     */
    TENCENT_COS("tencent","腾讯云存储"),
    /**
     * 七牛云QNC
     */
    QN_QNC("qn","七牛云存储"),
    /**
     * Minio
     */
    MINIO("minio","Minio存储");

    private String type;
    private String desc;

    UploadFileEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static UploadFileEnum parse(String type) {
        for (UploadFileEnum uploadFileEnum : UploadFileEnum.values()) {
            if (uploadFileEnum.getType().equals(type)) {
                return uploadFileEnum;
            }
        }
        return null;
    }

}
