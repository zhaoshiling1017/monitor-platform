package com.ducetech.app.service;

import com.ducetech.app.model.Fault;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.model.PagerRS;

/**
 * Created by lenzhao on 17-5-3.
 */
public interface FaultService {

    PagerRS<Fault> getFaultByPager(BaseQuery<Fault> query);

    Fault queryById(String faultId);
}
