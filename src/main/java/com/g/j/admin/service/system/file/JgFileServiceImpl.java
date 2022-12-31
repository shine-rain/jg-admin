package com.g.j.admin.service.system.file;

import com.g.j.admin.dao.system.file.JgFileMapper;
import com.g.j.admin.entity.system.vo.FileVo;
import com.g.j.admin.entity.system.dto.FileDto;
import com.g.j.admin.entity.system.po.JgFile;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Service
public class JgFileServiceImpl extends BaseServiceImpl<JgFileMapper, JgFile> implements IjgFileService {

    @Override
    public FileVo recordFileInfo(FileDto jgFileDto) {
        FileVo fileVo = new FileVo();
        JgFile jgFile = jgFileDto.toJgFile();
        save(jgFile);

        fileVo.setBatchId(jgFile.getBatchId());
        fileVo.setFileName(jgFile.getFileName());
        fileVo.setId(jgFile.getId());
        fileVo.setHostDomain(jgFile.getHostDomain());
        fileVo.setUrl(jgFile.getUrl());
        return fileVo;
    }
}
