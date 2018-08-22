package com.worthytrip.shopping;

/**
 * @author yuzhigang on 31/5/2018 5:26 PM.
 * @version 1.0
 * Description:
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableAsync
@MapperScan("com.worthytrip.shopping.dao.mapper")
public class UhetripSpringbootApplication {


    public static void main(String[] args) {
        SpringApplication.run(UhetripSpringbootApplication.class, args);
    }
}
