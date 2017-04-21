package com.ducetech.api.controller;

import com.ducetech.api.model.vo.AdvertiserVO;
import com.ducetech.framework.security.TokenManager;
import com.ducetech.framework.web.annotation.IgnoreSecurity;
import com.ducetech.framework.web.view.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by lenzhao on 17-2-2.
 */

@Controller
public class AdvertiserController {

    @Autowired
    private TokenManager tokenManager;

    @IgnoreSecurity
    @RequestMapping(value = "/api/advertisers", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult createAdvertiser(@RequestBody AdvertiserVO advertiserVO) {
        // 创建 token
        String token = tokenManager.createToken("10001");
        return OperationResult.buildConfirmResult(token, new Date());
    }

    @RequestMapping(value = "/api/advertisers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public OperationResult getAdvertiser(@PathVariable("id") String advertiserId) {
        return OperationResult.buildSuccessResult(advertiserId);
    }
}
