package com.ducetech.app.model;

import com.ducetech.framework.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenzhao on 17-4-27.
 */
@Data
public class Dictionary extends BaseModel implements Serializable {

    //ID
    private String dicId;
    //节点编码
    private String nodeCode;
    //节点名称
    private String nodeName;
    //序号
    private int nodeOrder;
    //是否删除
    private int isDeleted;
    //创建人ID
    private String creatorId;
    //更新人ID
    private String updatorId;
    //创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;
}
