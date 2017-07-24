package com.ducetech.app.service;

import com.ducetech.app.model.Fault;
import com.ducetech.app.model.vo.FaultVo;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.model.PagerRS;

import java.util.List;

/**
 * Created by lenzhao on 17-5-3.
 */
public interface FaultService {

    PagerRS<Fault> getFaultByPager(BaseQuery<Fault> query);

    Fault queryById(String faultId);

    List<FaultVo> getDeviceFaultsService(Fault fault);

    void saveFault(Fault fault);

    void updateFaultType(String faultId, String type);

    List<Fault> getFaults(Fault faultVO);
}
