package com.worthytrip.shopping.boot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author yuzhigang on 3/6/2018 10:15 PM.
 * @version 1.0
 * Description:
 */
@Component
public class Bootstrap implements ApplicationRunner {
    /**
     * Do initialization task here. The method will be invoked in the main thread
     * just after applicationcontext is created and before springboot application startup
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}

