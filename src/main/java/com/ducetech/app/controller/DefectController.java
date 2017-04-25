package com.ducetech.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.framework.controller.BaseController;

@Controller
public class DefectController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(DefectController.class);
	
	@RequestMapping(value = "/defects", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "defect/index";
	} 

}
