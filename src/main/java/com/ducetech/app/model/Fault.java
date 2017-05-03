package com.ducetech.app.model;

import com.ducetech.framework.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenzhao on 17-4-26.
 */

@Data
public class Fault extends BaseModel implements Serializable {

    //ID
    private String faultId;
    //线路
    private String line;
    //车号
    private String carNumber;
    //弓位置
    private String bowPosition;
    //区间
    private String interval;
    //环境温度
    private String envTemperature;
    //设备温度
    private String eqTemperature;
    //缺陷类型
    private String type;
    private Dictionary dic;
    //缺陷级别
    private String level;
    //图片路径
    private String imagePath;
    //缺陷信息
    private String faultInfo;
    //时间
    private Date createdAt;
    //机位号
    private String deviceNum;
    //是否删除
    private int isDeleted;


    private String beginTime;
    private String endTime;

}
