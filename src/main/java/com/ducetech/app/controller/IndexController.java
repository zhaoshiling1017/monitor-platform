package com.ducetech.app.controller;

import com.alibaba.fastjson.JSON;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.util.DateUtil;
import org.joda.time.LocalTime;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class IndexController extends BaseController {

	private Environment env;

	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) {
		getLoginUser(request, model);
		return "index";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String notFound() {
		return "error/404";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String error() {
		return "error/500";
	}

	@RequestMapping(value = "/401", method = RequestMethod.GET)
	public String unAuthorized() {
		return "error/401";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String forbidden() {
		return "error/403";
	}


	/**
	 * @Title: getDate
	 * @param response
	 * @throws java.io.IOException
	 * @Description: 获取当前时间
	 */
	@RequestMapping(value = "/getDate", method = RequestMethod.GET)
	public void getDate(HttpServletResponse response) throws IOException {
		response.getWriter().write(JSON.toJSONString(DateUtil.formatTime(new Date())));
	}

	@RequestMapping(value = "/sseTest", produces = "text/event-stream")
	@ResponseBody
	public String handleRequest () {
		return "data:" + LocalTime.now().toString() + "\n\n";
	}
}
