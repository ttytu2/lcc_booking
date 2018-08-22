package com.worthytrip.shopping.dao.druid;

/**
 * @author yuzhigang on 31/5/2018 5:26 PM.
 * @version 1.0
 * Description:
 * Druid static service configuration. http://ip/druid/index.html
 */
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidMonitorConfig {

    private String statUser;

    private String statPassword;

    public String getStatUser() {
        return statUser;
    }

    public void setStatUser(String statUser) {
        this.statUser = statUser;
    }

    public String getStatPassword() {
        return statPassword;
    }

    public void setStatPassword(String statPassword) {
        this.statPassword = statPassword;
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();

        return dsInterceptor;
    }

    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("com.worthytrip.shopping.dao.mapper.*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor,
                                                   JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean registrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        /** 初始化参数配置，initParams **/
        // 白名单,多个ip逗号隔开
        bean.addInitParameter("allow", "");
        // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
        // permitted to view this page.
        bean.addInitParameter("deny", "");
        // 登录查看信息的账号密码.
        bean.addInitParameter("loginUsername", this.statUser);
        bean.addInitParameter("loginPassword", this.statPassword);
        bean.addInitParameter("filters", "stat,wall");
        // 是否能够重置数据.
        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则.
        bean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息.
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return bean;
    }

}
