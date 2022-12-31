package com.g.j.admin.service.common.upload;

import cn.hutool.core.util.IdUtil;
import com.g.j.admin.entity.system.dto.JgUploadDto;
import com.g.j.admin.entity.system.dto.FileDto;
import com.g.j.admin.entity.system.dto.FileUploadDto;
import com.g.j.admin.entity.system.vo.FileVo;
import com.g.j.admin.entity.system.vo.UploadVo;
import com.g.j.admin.service.system.file.IjgFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Service
public class UploadServiceImpl implements IUploadService {

    @Autowired
    private IFileService iFileService;
    @Autowired
    private IjgFileService ijgFileService;

    @Override
    public UploadVo upload(MultipartFile file, JgUploadDto jgUploadDto) throws IOException {
        UploadVo uploadVo = new UploadVo();
        // 初始化文件 内容
        // 文件记录主键 文件的新名称
        String fileId = IdUtil.getSnowflakeNextIdStr();
        String suffix = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = fileId + suffix;
        String fileType = file.getContentType();

        // 调用文件上传服务
        FileUploadDto fileUploadDto = new FileUploadDto();
        fileUploadDto.setFileName(fileName);
        FileDto fileDto = iFileService.uploadFile(file.getInputStream(), fileUploadDto);

        // 返回前端数据
        uploadVo.setFileName(fileName);
        uploadVo.setId(fileId);
        uploadVo.setFileType(fileType);
        uploadVo.setSrc(fileDto.getHostDomain()+fileDto.getUrl());
        return uploadVo;
    }

    @Override
    public UploadVo uploadRecord(MultipartFile file, JgUploadDto jgUploadDto) throws IOException {
        UploadVo uploadVo = new UploadVo();
        // 初始化文件 内容
        // 文件记录主键 文件的新名称
        String fileId = IdUtil.getSnowflakeNextIdStr();
        String suffix = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = fileId + suffix;
        long totalSize = file.getSize();
        Integer size = Math.toIntExact(totalSize / 1024);
        String fileType = file.getContentType();
        String batchId = jgUploadDto.getBatchId();
        if (!StringUtils.hasText(jgUploadDto.getBatchId())) {
            batchId = IdUtil.simpleUUID();
        }

        // 调用文件上传服务
        FileUploadDto fileUploadDto = new FileUploadDto();
        fileUploadDto.setFileName(fileName);
        FileDto fileDto = iFileService.uploadFile(file.getInputStream(), fileUploadDto);

        // 记录上传文件信息
        fileDto.setId(fileId);
        fileDto.setFileName(fileName);
        fileDto.setFileType(fileType);
        fileDto.setBatchId(batchId);
        fileDto.setOriginalName(file.getOriginalFilename());
        fileDto.setSize(size);
        FileVo fileVo = ijgFileService.recordFileInfo(fileDto);

        // 返回前端数据
        uploadVo.setFileName(fileVo.getFileName());
        uploadVo.setId(fileVo.getId());
        uploadVo.setBatchId(fileVo.getBatchId());
        uploadVo.setFileType(fileVo.getFileType());
        uploadVo.setSrc(fileVo.getHostDomain()+fileVo.getUrl());
        return uploadVo;
    }
}
