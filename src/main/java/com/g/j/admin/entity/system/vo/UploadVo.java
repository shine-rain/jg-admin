package com.g.j.admin.entity.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class UploadVo implements Serializable {

    private String id;

    private String fileName;

    private String src;

    private String fileType;

    private String batchId;

}
