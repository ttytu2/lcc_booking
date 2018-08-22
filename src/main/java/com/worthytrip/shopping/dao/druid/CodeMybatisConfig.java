package com.worthytrip.shopping.dao.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.worthytrip.shopping.dao.mapper.codedata"}, sqlSessionTemplateRef  = "SqlSessionTemplateCode")
public class CodeMybatisConfig {
    @Bean(name = "codedataSource")
    @Qualifier("codedataSource")
    @ConfigurationProperties(prefix="spring.datasource.policy")
    public DataSource defaultDataSource() {
        return new DruidDataSource();
    }
    @Bean(name = "SqlSessionFactoryCode")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("codedataSource")DataSource ds) throws Exception {
            //解决myBatis下 不能嵌套jar文件的问题

            SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
            // 指定数据源(这个必须有，否则报错)
            fb.setDataSource(ds);
            String typeAliasesPackage = "com.worthytrip.shopping.dao.model.codedata;";
            fb.setTypeAliasesPackage(typeAliasesPackage);
            
            
            fb.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mapper/codedata/*.xml"));
            return fb.getObject();
    }


    @Bean(name = "TransactionManagerCode")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("codedataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionTemplateCode")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("SqlSessionFactoryCode") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
