package com.ducetech.framework.model;


import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;

public class BaseModel {

    public  static <T> BaseQuery<T> getSearchCondition(Class<T> clazz, HttpServletRequest request) throws Exception{
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        String search = request.getParameter("search");
        T t = JSON.parseObject(search, clazz);
        BaseQuery<T> query = new BaseQuery<T>(t);
        query.setPage(page);
        query.setRows(rows);
        return query;
    }
}
