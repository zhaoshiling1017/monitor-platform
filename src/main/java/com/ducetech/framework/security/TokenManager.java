package com.ducetech.framework.security;

/**
 * Created by lenzhao on 17-2-2.
 */
public interface TokenManager {

    String createToken(String userId);

    boolean checkToken(String token);
}
