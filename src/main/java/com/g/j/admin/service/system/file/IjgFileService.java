package com.g.j.admin.service.system.file;

import com.g.j.admin.entity.system.dto.FileDto;
import com.g.j.admin.entity.system.po.JgFile;
import com.g.j.admin.entity.system.vo.FileVo;
import com.g.j.admin.service.common.base.IBaseService;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IjgFileService extends IBaseService<JgFile> {

    FileVo recordFileInfo(FileDto fileDto);
}
