package com.ducetech.app.service.impl;

import com.ducetech.app.dao.DictionaryDAO;
import com.ducetech.app.model.Dictionary;
import com.ducetech.app.service.DictionaryService;
import com.ducetech.framework.util.ExtStringUtil;
import com.ducetech.framework.util.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lenzhao on 17-4-27.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryDAO dictionaryDAO;

    @Override
    public String createTree() {
        List<Dictionary> dics = dictionaryDAO.selectAllDictionarys();
        List<Map<String, Object>> lists = Lists.newArrayList();
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id", "000");
        map.put("pId", "-1");
        map.put("name", "缺陷库");
        lists.add(map);
        if (null != dics && !dics.isEmpty()) {
            for (Dictionary dic : dics) {
                map = Maps.newLinkedHashMap();
                map.put("id", dic.getNodeCode());
                map.put("pId", dic.getNodeCode().substring(0, dic.getNodeCode().length() - 3));
                map.put("name", dic.getNodeName());
                lists.add(map);
            }
        }
        return JsonUtil.writeValueAsString(lists);
    }

    @Override
    public Long getCountByPCode(String nodeCode) {
        return dictionaryDAO.getCountByPCode(nodeCode);
    }

    @Override
    public void deleteDic(String nodeCode) {
        dictionaryDAO.deleteDic(nodeCode);
    }

    @Override
    public List<Dictionary> querySubNodesByCode(String pCode) {
        return dictionaryDAO.querySubNodesByCode(pCode);
    }

    @Override
    public String getNodeCode(String pCode) {
        List<Dictionary> nodes = querySubNodesByCode(pCode);
        Iterator<Dictionary> dicIter = nodes.iterator();
        while (dicIter.hasNext()) {
            Dictionary dic = dicIter.next();
            if ("999".equals(dic.getNodeCode().substring(dic.getNodeCode().length() - 3))) {
                dicIter.remove();
            }
        }
        String newCode = "001";
        if (!nodes.isEmpty()) {
            Dictionary node = nodes.get(nodes.size() - 1);
            String nodeCode = node.getNodeCode();
            String subCode = nodeCode.substring(nodeCode.length() - 3, nodeCode.length());
            int current = Integer.parseInt(subCode);
            current = current + 1;
            newCode = ExtStringUtil.leftJoin(current, 3, "0");
        }

        return pCode + newCode;
    }

    @Override
    public void saveDic(Dictionary dic) {
        dictionaryDAO.saveDic(dic);
    }

    @Override
    public Dictionary findByCode(String nodeCode) {
        List<Dictionary> dics = dictionaryDAO.findByCode(nodeCode);
        if (null != dics && dics.size() > 0) {
            return dics.get(0);
        }
        return null;
    }

    @Override
    public void updateDic(Dictionary dic) {
        dictionaryDAO.updateDic(dic);
    }

    @Override
    public List<Dictionary> queryAll() {
        return dictionaryDAO.queryAll();
    }
}
