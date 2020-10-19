package com.github.yujiaao.demo;

import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xwx
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        System.out.println("WebConfig!!!!");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpHeaderSecurityFilter());
        registration.addUrlPatterns("/pc/*");
        registration.addInitParameter("blockContentTypeSniffingEnabled", "true");
        registration.setName("httpHeaderSecurity");
        registration.setOrder(1);
        return registration;
    }


}
