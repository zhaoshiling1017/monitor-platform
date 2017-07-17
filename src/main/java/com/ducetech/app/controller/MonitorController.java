package com.ducetech.app.controller;

import javax.servlet.http.HttpServletRequest;

import com.ducetech.app.model.Fault;
import com.ducetech.app.model.vo.FaultVo;
import com.ducetech.app.service.FaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.framework.controller.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MonitorController extends BaseController {
	@Autowired
	FaultService faultService;

	private static final Logger LOG = LoggerFactory.getLogger(MonitorController.class);
	
	@RequestMapping(value = "/monitors", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "monitor/index";
	}

	@RequestMapping(value = "/monitorDatas",method = RequestMethod.GET)
	@ResponseBody
	public List<FaultVo> monitorDatas(Fault fault) {
		Map<Integer,List<Fault>> map = new TreeMap<Integer,List<Fault>>();
		List<FaultVo> faultVos = faultService.getDeviceFaultsService(fault);
		return faultVos;
	}
}
