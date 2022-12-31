package com.g.j.admin.service.common.upload;

import com.g.j.admin.config.UploadConfig;
import com.g.j.admin.entity.system.dto.FileDto;
import com.g.j.admin.entity.system.dto.FileUploadDto;

import java.io.InputStream;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public class OssFileServiceImpl implements IFileService {


    public OssFileServiceImpl(UploadConfig.OssConfig config) {

    }


    @Override
    public FileDto uploadFile(InputStream inputStream, FileUploadDto jgFileDto) {
        FileDto fileDto = new FileDto();

        return fileDto;
    }

    @Override
    public void deleteFile(String fileId) {

    }
}
