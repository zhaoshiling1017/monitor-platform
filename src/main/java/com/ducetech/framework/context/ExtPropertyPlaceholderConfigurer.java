package com.ducetech.framework.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import java.util.*;

/**
 * 扩展标准的PropertyPlaceholderConfigurer把属性文件中的配置参数信息放入全局Map变量便于其他接口访问key-value配置数据
 */
public class ExtPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ExtPropertyPlaceholderConfigurer.class);

    private static Map<String, String> ctxPropertiesMap;

    private static String basePackages;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, String>();
        logger.info("Putting PropertyPlaceholder {}  datas into cache...", props.size());
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
            if (keyStr.startsWith("env.") || keyStr.startsWith("env_")) {
                System.setProperty(keyStr, value);
            }
        }
    }

    public Map<String, String> getPropertiesMap() {
        return ctxPropertiesMap;
    }

    public String getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public void setBasePackages(String basePackages) {
        ExtPropertyPlaceholderConfigurer.basePackages = basePackages;
    }

    public static String getBasePackages() {
        return basePackages;
    }
}
