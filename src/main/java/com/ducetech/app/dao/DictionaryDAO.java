package com.ducetech.app.dao;

import com.ducetech.app.model.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lenzhao on 17-4-27.
 */
public interface DictionaryDAO {

    /**
     * 获取所有节点
     * @return
     */
    List<Dictionary> selectAllDictionarys();

    /**
     * 获取父节点记录数
     * @param nodeCode
     * @return
     */
    Long getCountByPCode(@Param("nodeCode") String nodeCode);

    /**
     * 删除节点
     * @param nodeCode
     */
    void deleteDic(String nodeCode);

    /**
     * 根据父节点获取子节点列表
     * @param pCode
     * @return
     */
    List<Dictionary> querySubNodesByCode(String pCode);

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
    List<Dictionary> findByCode(String nodeCode);

    /**
     * 更新节点
     * @param dic
     */
    void updateDic(Dictionary dic);

    /**
     * 获取所有节点列表
     * @return
     */
    List<Dictionary> queryAll();
}
