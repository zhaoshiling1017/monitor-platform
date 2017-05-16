package com.ducetech.api.controller;

import com.ducetech.api.model.vo.AdvertiserVO;
import com.ducetech.framework.security.TokenManager;
import com.ducetech.framework.support.service.DynamicConfigService;
import com.ducetech.framework.web.annotation.IgnoreSecurity;
import com.ducetech.framework.web.view.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by lenzhao on 17-2-2.
 */

@Controller
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private DynamicConfigService dynamicConfigService;

    @IgnoreSecurity
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    @ResponseBody
    public OperationResult test(String name) {
        return OperationResult.buildSuccessResult("SUCC...", 0);
    }

    @IgnoreSecurity
    @RequestMapping(value = "/api/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult  springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
        String imagePath = dynamicConfigService.getString("image_path", "/data/image/");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path= imagePath + file.getOriginalFilename();
                    file.transferTo(new File(path));
                }
            }
        }
        return OperationResult.buildSuccessResult("", 0);
    }
}
