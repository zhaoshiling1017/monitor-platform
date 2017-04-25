package com.ducetech.app.controller;

import com.ducetech.framework.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenzhao on 17-4-25.
 */
@Controller
public class FaultController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(FaultController.class);

    @RequestMapping(value = "/faults", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        return "fault/index";
    }
}
