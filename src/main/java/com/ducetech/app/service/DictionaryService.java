package com.ducetech.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ducetech.app.model.Dictionary;
import com.ducetech.framework.util.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by lenzhao on 17-4-27.
 */
public interface DictionaryService {

    /**
     * 构造节点树
     * @return
     */
    String createTree();

    /**
     * 获取父节点记录数
     * @param nodeCode
     * @return
     */
    Long getCountByPCode(String nodeCode);

    /**
     * 删除节点
     * @param nodeCode
     */
    void deleteDic(String nodeCode);

    /**
     * 根据父节点获取所有子节点
     * @param pCode
     * @return
     */
    List<Dictionary> querySubNodesByCode(String pCode);

    /**
     * 获取新的节点编码
     * @param pCode
     * @return
     */
    String getNodeCode(String pCode);

    /**
     * 持久化节点
     * @param dic
     */
    void saveDic(Dictionary dic);

    /**
     * 根据节点编码获取节点
     * @param nodeCode
     * @return
     */
    Dictionary findByCode(String nodeCode);

    /**
     * 更新节点
     * @param dic
     */
    void updateDic(Dictionary dic);

    /**
     * 获取所有节点
     * @return
     */
    List<Dictionary> queryAll();

}
