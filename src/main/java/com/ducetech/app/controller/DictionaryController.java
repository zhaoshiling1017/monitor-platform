package com.ducetech.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.ducetech.app.model.Dictionary;
import com.ducetech.app.model.User;
import com.ducetech.app.model.vo.DictionaryQuery;
import com.ducetech.app.service.DictionaryService;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.web.view.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenzhao on 17-4-27.
 */
@Controller
public class DictionaryController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/dics", method = RequestMethod.GET)
    public String index(DictionaryQuery query, Model model) {
        if (null == query) {
            query = new DictionaryQuery();
        }
        String nodes = dictionaryService.createTree();
        model.addAttribute("nodes", nodes);
        return "dictionary/index";
    }

    @RequestMapping(value = "/dics/{nodeCode}/beforeDel", method = RequestMethod.GET)
    @ResponseBody
    public OperationResult beforeDel(@PathVariable(value="nodeCode") String nodeCode) {
        Long count = dictionaryService.getCountByPCode(nodeCode);
        if (count > 0) {
            return OperationResult.buildFailureResult("该节点或其子节点被关联中，删除失败！", 0);
        } else {
            return OperationResult.buildSuccessResult("", 1);
        }
    }

    @RequestMapping(value = "/dics/{nodeCode}", method = RequestMethod.DELETE)
    @ResponseBody
    public OperationResult destory(@PathVariable(value="nodeCode") String nodeCode) {
        dictionaryService.deleteDic(nodeCode);
        return OperationResult.buildSuccessResult("节点删除成功！", 1);
    }

    @RequestMapping(value = "/dics", method = RequestMethod.POST)
    @ResponseBody
    public Dictionary create(HttpServletRequest request, String name, @RequestParam(value = "pId") String pCode) {
        String nodeCode = dictionaryService.getNodeCode(pCode);
        User user = getLoginUser(request);
        Dictionary dic = new Dictionary();
        dic.setCreatedAt(new Date());
        dic.setCreatorId(user.getUserId());
        dic.setUpdatorId(user.getUserId());
        dic.setIsDeleted(0);
        dic.setNodeCode(nodeCode);
        dic.setNodeName(name);
        dic.setNodeOrder(1);
        dic.setUpdatedAt(new Date());
        dictionaryService.saveDic(dic);
        return dic;
    }

    @RequestMapping(value = "/dics/{nodeCode}", method = RequestMethod.PUT)
    @ResponseBody
    public OperationResult update(HttpServletRequest request, @PathVariable(value="nodeCode") String nodeCode, String nodeName) {
        User user = getLoginUser(request);
        Dictionary dic = dictionaryService.findByCode(nodeCode);
        if (null != dic) {
            dic.setNodeName(nodeName);
            dic.setUpdatedAt(new Date());
            dic.setUpdatorId(user.getUserId());
            dictionaryService.updateDic(dic);
        }
        return OperationResult.buildSuccessResult("", 1);
    }
}
