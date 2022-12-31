package com.g.j.admin.service.common.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.g.j.admin.config.UploadConfig;
import com.g.j.admin.entity.system.dto.FileDto;
import com.g.j.admin.entity.system.dto.FileUploadDto;
import com.g.j.admin.enums.UploadFileEnum;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public class LocalFileServiceImpl implements IFileService {

    private final UploadConfig.LocalConfig config;

    public LocalFileServiceImpl(UploadConfig.LocalConfig config) {
        this.config = config;
    }

    @Override
    public FileDto uploadFile(InputStream inputStream, FileUploadDto jgFileUploadDto) {
        FileDto fileDto = new FileDto();
        String time = DateUtil.today();
        File path = new File(config.getUploadPath() + "/" + time);
        if (!path.exists()) {
            if (path.mkdirs()) {
                File file = FileUtil.writeFromStream(inputStream, config.getUploadPath() + "/" + time + "/" + jgFileUploadDto.getFileName());
                fileDto.setHostDomain(config.getDomainUrl());
                fileDto.setUrl(time + "/" + jgFileUploadDto.getFileName());
                fileDto.setStoreType(UploadFileEnum.LOCAL.getType());
            }
        } else {
            File file = FileUtil.writeFromStream(inputStream, config.getUploadPath() + "/" + time + "/" + jgFileUploadDto.getFileName());
            fileDto.setHostDomain(config.getDomainUrl());
            fileDto.setUrl(time + "/" + jgFileUploadDto.getFileName());
            fileDto.setStoreType(UploadFileEnum.LOCAL.getType());
        }
        return fileDto;
    }

    @Override
    public void deleteFile(String fileId) {

    }
}
