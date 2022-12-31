package com.g.j.admin.service.common.upload;

import com.g.j.admin.entity.system.dto.JgUploadDto;
import com.g.j.admin.entity.system.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IUploadService {

    /**
     * 上传文件 仅做上传 不进行数据库存储记录
     * @param file 文件流
     * @param jgUploadDto 参数数据
     * @return 结果
     */
    UploadVo upload(MultipartFile file, JgUploadDto jgUploadDto) throws IOException;

    /**
     * 上传文件 并记录文件信息至数据库
     * @param file 文件流
     * @param jgUploadDto 参数
     * @return 结果
     * @throws IOException 异常
     */
    UploadVo uploadRecord(MultipartFile file, JgUploadDto jgUploadDto) throws IOException;
}
