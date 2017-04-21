package com.ducetech.framework.cons;

import com.ducetech.framework.annotation.MetaData;
import com.google.common.collect.Maps;

import java.util.Map;

public class GlobalConstant {

	public static final Map<Boolean, String> booleanLabelMap = Maps.newLinkedHashMap();
	static {
		booleanLabelMap.put(Boolean.TRUE, "是");
		booleanLabelMap.put(Boolean.FALSE, "否");
	}

	//性别
	public static enum GenderEnum {
		@MetaData(value = "男")
		M,

		@MetaData(value = "女")
		F,

		@MetaData(value = "保密")
		U;
	}

	@MetaData("ConfigProperty:系统标题名称")
	public final static String cfg_system_title = "cfg_system_title";

	@MetaData("ConfigProperty:是否禁用管理账号注册功能")
	public final static String cfg_mgmt_signup_disabled = "cfg_mgmt_signup_disabled";


	@MetaData("ConfigProperty:是否全局禁用开放手机号短信发送功能")
	public final static String cfg_public_send_sms_disabled = "cfg_public_send_sms_disabled";

	@MetaData("数据字典:消息类型")
	public final static String DataDict_Message_Type = "DataDict_Message_Type";

	//redis频道  系统频道
	public static final  String SYS_CHANNEL_MAIN="SYS_CHANNEL_MAIN";

	//公共
	public static final  String NOT_DELETE = "0";    	//未删除
	public static final  String DELETE = "1";			//已删除


	public static final String SYSTEM_PROP_FILE_NAME = "system.properties";

	public static final String API_PATH_PREFIX = "/api";
}
