package com.ducetech.framework.support.service;

import com.ducetech.app.model.Role;
import com.ducetech.app.model.User;
import com.ducetech.app.service.RoleService;
import com.ducetech.app.service.UserService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenzhao on 17-1-19.
 */

@Service
public class CacheService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public static final String DIC_USER_LIST_NAME = "DIC_USER_LIST_NAME";

    public static final String DIC_ROLE_LIST_NAME = "DIC_ROLE_LIST_NAME";

    public static final String DIC_ROLE_USER_LIST_NAME = "DIC_ROLE_USER_LIST_NAME";

    private static final Cache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(1000).build();

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    /**
     * 获取缓存中的用户信息
     * @param userId
     * @return
     */
    public User getUserFromCache(String userId) {
        Object obj = null;
        User user = null;
        try {
            obj = cache.get(DIC_USER_LIST_NAME, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    List<User> users = userService.getAllUsers();
                    Map<String, User> map = new HashMap<String, User>();
                    if (null != users && !users.isEmpty()) {
                        for (User u : users) {
                            map.put(u.getUserId(), u);
                        }
                    }
                    return map;
                }
            });
        } catch (ExecutionException e) {
            logger.error("从缓存中获取用户列表异常", e);
        }
        if (null != obj) {
            user = ((Map<String, User>) obj).get(userId);
        }
        return user;
    }

    /**
     * 获取缓存中的角色信息
     * @param roleId
     * @return
     */
    public Role getRoleFromCache(String roleId) {
        Object obj = null;
        Role role = null;
        try {
            obj = cache.get(DIC_ROLE_LIST_NAME, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    List<Role> roles = roleService.getAllRoles();
                    Map<String, Role> map = new HashMap<String, Role>();
                    if (null != roles && !roles.isEmpty()) {
                        for (Role r : roles) {
                            map.put(r.getRoleId(), r);
                        }
                    }
                    return map;
                }
            });
        } catch (ExecutionException e) {
            logger.error("从缓存中获取角色列表异常", e);
        }
        if (null != obj) {
            role = ((Map<String, Role>) obj).get(roleId);
        }
        return role;
    }

    /**
     * 根据角色从缓存中获取用户列表
     * @param roleId
     * @return
     */
    public List<User> getUsersByRoleIdFromCache(final String roleId) {
        Object obj = null;
        List<User> users = null;
        try {
            obj = cache.get(DIC_ROLE_USER_LIST_NAME, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    List<Role> roles = roleService.getAllRoles();
                    Map<String, List<User>> map = new HashMap<String, List<User>>();
                    if (null != roles && !roles.isEmpty()) {
                        for (Role r : roles) {
                            List<User> us = userService.getUsersByRoleId(r.getRoleId());
                            map.put(r.getRoleId(), us);
                        }
                    }
                    return map;
                }
            });
        } catch (ExecutionException e) {
            logger.error("从缓存中根据角色获取用户列表异常", e);
        }
        if (null != obj) {
            users = ((Map<String, List<User>>)obj).get(roleId);
        }
        return users;
    }

    /**
     * 根据键值清除缓存
     * @param key
     */
    public static void cleanUp(String key) {
        cache.invalidate(key);
    }

}
