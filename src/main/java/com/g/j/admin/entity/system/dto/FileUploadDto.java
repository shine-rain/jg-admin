package com.g.j.admin.entity.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class FileUploadDto implements Serializable {

    /**
     * 文件名称
     */
    private String fileName;

}
