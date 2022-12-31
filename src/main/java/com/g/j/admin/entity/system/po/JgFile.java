package com.g.j.admin.entity.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jg_file")
public class JgFile implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String fileName;

    private Integer fileSize;

    private String fileType;

    private String storeType;

    private String hostDomain;

    private String source;

    private Boolean bitPublic;

    private String url;

    private String batchId;

    private Date createDate;

    private String createUserId;

    private String originalName;

}