package com.ducetech.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtil {

    private final static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    public static void sleepOneSecond() {
        sleepSeconds(1);
    }

    public static void sleepSeconds(int seconds) {
        try {
            logger.debug("Thread {} sleep {} seconds...", Thread.currentThread().getName(), seconds);
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
