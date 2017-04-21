package com.ducetech.framework.schedule;


import com.ducetech.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 自定义Quartz Job业务对象的基类定义
 * 业务Job继承此抽象基类，获得Spring ApplicationContext的能力从而可以获取Spring声明的Bean对象
 * 同时实现QuartzJobBean约定接口，编写定时处理逻辑
 */
public abstract class BaseQuartzJobBean extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(BaseQuartzJobBean.class);

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.debug("Invoking executeInternalBiz for {}", this.getClass());

            // Process @Autowired injection for the given target object, based on the current web application context. 
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

            String result = executeInternalBiz(context);

            if (context != null && StringUtils.isNotBlank(result)) {
                context.setResult(result);
            }
            logger.debug("Job execution result: {}", result);
        } catch (Exception e) {
            logger.error("Quartz job execution error", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 定时任务内部逻辑实现，注意整个方法不在事务控制边界，因此如果涉及到多数据更新逻辑注意需要把所有业务逻辑封装到相关Service接口中，然后在此方法中一次性调用确保事务控制
     * @param context
     * @return 组装好的任务记录结果信息，可以调用 buildJobResultByTemplate 方法基于Freemarker模板组装复杂的响应文本信息
     */
    protected abstract String executeInternalBiz(JobExecutionContext context);
}
