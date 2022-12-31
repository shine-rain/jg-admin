package com.g.j.admin.entity.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class JgUploadDto implements Serializable {
    /**
     * 批次号
     */
    private String batchId;

    /**
     * 文件类型
     */
    private String fileType;
}
