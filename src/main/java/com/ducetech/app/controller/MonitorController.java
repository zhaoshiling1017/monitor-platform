package com.ducetech.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.framework.controller.BaseController;

@Controller
public class MonitorController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(MonitorController.class);
	
	@RequestMapping(value = "/monitors", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "monitor/index";
	} 

}
