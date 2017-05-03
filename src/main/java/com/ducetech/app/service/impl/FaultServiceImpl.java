package com.ducetech.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.ducetech.app.dao.DictionaryDAO;
import com.ducetech.app.dao.FaultDAO;
import com.ducetech.app.model.Fault;
import com.ducetech.app.service.FaultService;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.util.ExtStringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenzhao on 17-5-3.
 */
@Service
public class FaultServiceImpl implements FaultService {

    @Autowired
    private FaultDAO faultDAO;
    @Autowired
    private DictionaryDAO dictionaryDAO;

    @Override
    public PagerRS<Fault> getFaultByPager(BaseQuery<Fault> query) {

        if( query != null && query.getPage() > 0 ){			//如果传入offset大于0,则启用分页查询，否则不启用
            PageHelper.startPage(query.getPage(), query.getRows(), true);
        }
        Fault faultVO = query.getT();
        if (!ExtStringUtil.isBlank(faultVO.getBeginTime())) {
            faultVO.setBeginTime(faultVO.getBeginTime() + " 00:00:00");
        }
        if (!ExtStringUtil.isBlank(faultVO.getEndTime())) {
            faultVO.setEndTime(faultVO.getEndTime() + " 23:59:59");
        }
        List<Fault> faultList = faultDAO.selectFault(faultVO);
        for (Fault fault : faultList) {
            if (!ExtStringUtil.isBlank(fault.getType())) {
                fault.setDic(dictionaryDAO.findByCode(fault.getType()).get(0));
            }
        }
        PageInfo page = new PageInfo(faultList);
        PagerRS<Fault> pagerRS = new PagerRS<>(faultList, page.getTotal(), page.getPages());
        return pagerRS;
    }

    @Override
    public Fault queryById(String faultId) {
        return faultDAO.queryById(faultId).get(0);
    }
}
