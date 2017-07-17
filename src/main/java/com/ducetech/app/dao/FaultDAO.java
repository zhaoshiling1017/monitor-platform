package com.ducetech.app.dao;

import com.ducetech.app.model.Fault;
import com.ducetech.app.model.vo.FaultVo;

import java.util.List;

/**
 * Created by lenzhao on 17-5-3.
 */
public interface FaultDAO {

    List<Fault> selectFault(Fault fault);

    List<Fault> queryById(String faultId);

    List<FaultVo> selectDeviceFaults(Fault fault);

    void saveFault(Fault fault);
}
