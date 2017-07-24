package com.ducetech.app.controller;

import com.ducetech.app.model.Dictionary;
import com.ducetech.app.model.Fault;
import com.ducetech.app.service.DictionaryService;
import com.ducetech.app.service.FaultService;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.web.view.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lenzhao on 17-4-25.
 */
@Controller
public class FaultController extends BaseController {

    @Autowired
    private FaultService faultService;
    @Autowired
    private DictionaryService dictionaryService;

    private static final Logger LOG = LoggerFactory.getLogger(FaultController.class);

    @RequestMapping(value = "/faults/index", method = RequestMethod.GET)
    public String index(Model model) {
        List<Dictionary> dics = dictionaryService.queryAll();
        model.addAttribute("dics", dics);
        String nodes = dictionaryService.createTree();
        model.addAttribute("nodes", nodes);
        return "fault/index";
    }

    @RequestMapping(value = "/faults/{id}", method = RequestMethod.GET)
    public String show(@PathVariable(value = "id") String faultId, Model model) {
        Fault fault = faultService.queryById(faultId);
        fault.setDic(dictionaryService.findByCode(fault.getType()));
        model.addAttribute("fault", fault);
        return "fault/show";
    }

    @RequestMapping(value = "/faults", method = RequestMethod.GET)
    @ResponseBody
    public PagerRS<Fault> faultData(HttpServletRequest request) throws Exception {
        BaseQuery<Fault> query = Fault.getSearchCondition(Fault.class, request);
        PagerRS<Fault> rs = faultService.getFaultByPager(query);
        return rs;
    }
    @RequestMapping(value = "/faults/{id}/updateFaultType", method = RequestMethod.PUT)
    @ResponseBody
    public OperationResult updateFaultType(@PathVariable(value = "id") String faultId, String type) {
        faultService.updateFaultType(faultId, type);
        return OperationResult.buildSuccessResult("缺陷类型更新成功", 1);
    }

}
