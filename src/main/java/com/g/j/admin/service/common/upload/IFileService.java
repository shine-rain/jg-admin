package com.g.j.admin.service.common.upload;

import com.g.j.admin.entity.system.dto.FileDto;
import com.g.j.admin.entity.system.dto.FileUploadDto;

import java.io.InputStream;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IFileService {

    /**
     * 上传文件
     * @param inputStream 文件流
     * @param jgFileDto 参数对象
     * @return result
     */
    FileDto uploadFile(InputStream inputStream, FileUploadDto jgFileDto);

    /**
     * 删除文件
     * @param fileId 文件主键
     */
    void deleteFile(String fileId);

}
