package com.ducetech.framework.security.impl;

import com.ducetech.framework.security.TokenManager;
import com.ducetech.framework.support.service.RedisService;
import com.ducetech.framework.util.CodecUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTokenManager implements TokenManager {

    private static final long DEFAULT_SECONDS = 0L;

    private long seconds = DEFAULT_SECONDS;

    @Autowired
    private RedisService redisService;

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    @Override
    public String createToken(String userId) {
        String token = CodecUtil.createUUID();
        if (seconds != 0L) {
            redisService.setx(token, userId, seconds);
        } else {
            redisService.setItem(token, userId);
        }
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        boolean result;
        result = redisService.exists(token);
        if (seconds != 0) {
            result = redisService.expire(token, seconds);
        }
        return result;
    }
}
