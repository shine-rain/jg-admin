package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.po.JgFile;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class FileDto implements Serializable {

    private String hostDomain;

    private String url;

    private String storeType;

    private String fileType;

    private String source;

    private String fileName;

    private String id;

    private String batchId;

    private String originalName;

    private Integer size;

    public JgFile toJgFile() {
        JgFile jgFile = new JgFile();
        jgFile.setId(this.id).setFileName(this.fileName).setFileSize(this.size)
                .setFileType(this.fileType).setBatchId(this.batchId).setHostDomain(this.hostDomain)
                .setUrl(this.url).setOriginalName(this.originalName).setStoreType(this.storeType);

        return jgFile;
    }

}
