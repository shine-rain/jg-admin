package com.g.j.admin.controller.system.common;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.entity.system.dto.JgUploadDto;
import com.g.j.admin.entity.system.vo.UploadVo;
import com.g.j.admin.service.common.upload.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/system/upload")
public class UploadController {

    @Autowired
    private IUploadService iUploadService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult upload(MultipartFile file, JgUploadDto jgUploadDto) throws IOException {
        UploadVo uploadVo = iUploadService.upload(file, jgUploadDto);
        return SaResult.data(uploadVo);
    }

    @RequestMapping(value = "record", method = RequestMethod.POST, produces = "application/json")
    public SaResult uploadRecord(MultipartFile file, JgUploadDto jgUploadDto) throws IOException {
        UploadVo uploadVo = iUploadService.uploadRecord(file, jgUploadDto);
        return SaResult.data(uploadVo);
    }
}
