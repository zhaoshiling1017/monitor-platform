package com.ducetech.framework.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;


public final class RandomUtil {

    private RandomUtil() {
    }

    /**
     * 生成随机数
     */
    public static String getRandom(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    public static String UID() {
        return UUID.randomUUID().toString();
    }
}
