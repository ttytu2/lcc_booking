package com.worthytrip.shopping.util;

import com.worthytrip.commons.http.client.HttpClient;
import com.worthytrip.shopping.execution.config.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProxyUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProxyUtil.class);

    @Autowired
    private static BaseConfig config;

    @Autowired
    public void setBaseConfig(BaseConfig config) {
        ProxyUtil.config = config;
    }

    /**
     * false为代理未切换中
     *
     * @date 18-4-26 上午10:36
     */
    private static boolean SWITCH_STATUS = false;

    /**
     * 失败次数,达到配置的允许失败后主动触发切换代理逻辑
     *
     * @date 18-4-26 上午10:36
     */
    private static int FAILED_COUNT;

    public static boolean isSwitchStatus() {
        return SWITCH_STATUS;
    }

    private static void init() {
        FAILED_COUNT = 0;
        SWITCH_STATUS = false;
    }

    private static void setSwitchStatus() {
        ProxyUtil.SWITCH_STATUS = true;
    }

    public static synchronized void addFailedCountAndSwitchProxy() {
        FAILED_COUNT += 1;
        logger.info("FAILED_COUNT:{}", FAILED_COUNT);
        if (FAILED_COUNT >= config.getFailedCount()) {
            switchProxy();
        }
    }

    private static void switchProxy() {
        setSwitchStatus();
        try {
            logger.info("切换IP代理生效中...");
            String res = HttpClient.executeByGet(config.getSwitchProxyUrl());
            Thread.sleep(config.getWaitProxySwitchTime());
            logger.info("切换代理为:{}", res);
        } catch (Exception e) {
            logger.error("切换IP失败:", e);
        } finally {
            init();
            logger.info("初始化代理状态...");
        }
    }

}
