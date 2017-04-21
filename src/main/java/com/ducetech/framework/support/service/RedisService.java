package com.ducetech.framework.support.service;

import com.ducetech.framework.cons.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	public void setItem(String key, Object value){
		redisTemplate.opsForValue().set(key, value);
	}

	public Object getItem(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void sendMessage(String userId , String noticeType){
		redisTemplate.convertAndSend(GlobalConstant.SYS_CHANNEL_MAIN, userId+":"+noticeType);
	}
	
	public boolean expire(String key, long l) {
		return redisTemplate.expire(key, l, TimeUnit.MINUTES);
	}

	public void delDic(String key) {
		redisTemplate.delete(key);
	}

	public void clearByDicName(String dicName) {
		redisTemplate.delete("*" + dicName + "*");
	}

	public void loadByDicName(String dicName) {

	}

	public void delDicKey(String dicName, String key) {
		redisTemplate.opsForHash().delete(dicName, key);
	}

	public Set<String> getKeys(String dicName) {
		return redisTemplate.keys(dicName);
	}

	public String getValueByKey(String dicName, String key) {
		return redisTemplate.opsForHash().get(dicName, key).toString();
	}

	public Map<Object, Object> getAllByDicName(String dicName) {
		return redisTemplate.opsForHash().entries(dicName);
	}

	public boolean hexists(String dicName, String key) {
		return redisTemplate.opsForHash().hasKey(dicName, key);
	}

	public void setValueByKey(String dicName, String key, String value) {
		redisTemplate.opsForHash().put(dicName, key, value);
	}

	public long rpush(String dicName, String value) {
		return redisTemplate.opsForList().rightPush(dicName, value);
	}

	public long lpush(String dicName, String value) {
		return redisTemplate.opsForList().leftPush(dicName, value);
	}

	public String rpop(String dicName) {
		return redisTemplate.opsForList().rightPop(dicName).toString();
	}

	public String lpop(String dicName) {
		return redisTemplate.opsForList().leftPop(dicName).toString();
	}

	public long llen(String dicName) {
		return redisTemplate.opsForList().size(dicName);
	}

	public void hmset(String dicName, Map<String, String> elements) {
		redisTemplate.opsForHash().putAll(dicName, elements);
	}

	public List<Object> hmget(String dicName, Object[] keys) {
		return redisTemplate.opsForHash().multiGet(dicName, Arrays.asList(keys));
	}

	public void publish(String channel, String message) {
		redisTemplate.convertAndSend(channel, message);
	}

	public void setx(String key, Object value, long l) {
		redisTemplate.opsForValue().set(key, value, l, TimeUnit.MINUTES);
	}

	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}
}
