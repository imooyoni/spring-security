package com.moonie.authorization.common.config;

import com.moonie.authorization.util.UniqueKeyFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {
    Logger logger = LogManager.getLogger(UniqueKeyFilter.class);

    @Bean
    public FilterRegistrationBean<UniqueKeyFilter> loggingFilter(){

        logger.debug("Generated uniqueId: " + ThreadContext.get("uniqueId"));

        FilterRegistrationBean<UniqueKeyFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UniqueKeyFilter());
        registrationBean.addUrlPatterns("/*"); // 특정 URL 패턴만 필터링 가능
        registrationBean.setOrder(1); // 필터 순서 지정 가능

        return registrationBean;
    }
}