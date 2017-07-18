package com.ducetech.app.web.listener;

import com.alibaba.fastjson.JSONObject;
import com.ducetech.app.model.Fault;
import com.ducetech.app.service.FaultService;
import com.ducetech.framework.context.SpringContextHolder;
import com.ducetech.framework.support.service.DynamicConfigService;
import com.ducetech.framework.util.ExtStringUtil;
import com.ducetech.framework.util.NumberUtil;
import com.ducetech.framework.web.view.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenzhao on 17-6-16.
 */
public class StartupListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(StartupListener.class);
    private static final ExecutorService exec = Executors.newFixedThreadPool(1);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        exec.submit(new UploadImageServer());
        LOG.info("Upload image server is Started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.debug("Invoke contextDestroyed contextDestroyed");
        exec.shutdown();
    }

    public static class UploadImageServer implements Runnable {
        private int port;
        private RedisTemplate<String, Object> redisTemplate;
        private DynamicConfigService dynamicConfigService;
        private ServerSocket ss = null;
        private Socket socket = null;
        private BufferedInputStream bis = null;
        private BufferedOutputStream bos = null;
        private PrintWriter pw = null;
        //FaultService
        private FaultService faultService;

        public UploadImageServer() {
            ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
            redisTemplate = (RedisTemplate<String, Object>) applicationContext.getBean("redisTemplate");
            dynamicConfigService = (DynamicConfigService) applicationContext.getBean("dynamicConfigService");
            this.port = Integer.valueOf(dynamicConfigService.getString("tcp.port", "9100"));
        }

        @Override
        public void run() {
            try {
                ss = new ServerSocket(port);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
            if (null != ss) {
                while (true) {
                    try {
                        socket = ss.accept();
                        bis = new BufferedInputStream(socket.getInputStream());
                        //图片格式都是.bmp
                        //TODO
                        //String imagePath = dynamicConfigService.getString("fault_image_path", "/data/image/fault/") + ExtStringUtil.batchNoGenerator("DIC.FAULT_IMAGE_NAME_GEN", "0", 4, redisTemplate) + dynamicConfigService.getString("fault_image_sufix", ".bmp");
                        String imagePath = "D://data/test.bmp";
                        bos = new BufferedOutputStream(new FileOutputStream(imagePath));
                        byte[] dataLen = new byte[2];
                        bis.read(dataLen);
                        byte[] dataBytes = new byte[NumberUtil.byte2Short(dataLen)];
                        int resultLen = bis.read(dataBytes);
                        String params = new String(dataBytes, 0, resultLen,"GBK");
                        //TODO 把图片属性及图片路径插入数据库
                        LOG.info("fault image params {}.", params);

                        Fault fault = JSONObject.parseObject(params, Fault.class);
                        fault.setIsDeleted(0);
                        fault.setImagePath(imagePath);

                        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
                        faultService = (FaultService) applicationContext.getBean("faultServiceImpl");
                        faultService.saveFault(fault);

                        byte[] buf = new byte[1024];
                        int len = 0;
                        while ((len = bis.read(buf)) != -1) {
                            bos.write(buf, 0, len);
                        }
                        bos.flush();
                        //暂时不回传数据
                        //pw = new PrintWriter(socket.getOutputStream(),true);
                        //pw.println(JSONObject.toJSONString(OperationResult.buildSuccessResult("", 1)));
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    } finally {
                        try {
                            if (null != bos) {
                                bos.close();
                            }
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                        try {
                            if (null != bis) {
                                bis.close();
                            }
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                        try {
                            if (null != socket) {
                                socket.close();
                            }
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                        try {
                            if (null != pw) {
                                pw.close();
                            }
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }
}
