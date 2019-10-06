package com.github.yujiaao.reloading.config;
  
import com.github.yujiaao.reloading.filter.GlobalFilter;
import com.github.yujiaao.reloading.listener.ContextListener;
import com.github.yujiaao.reloading.listener.WebServerStartedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebFilter;

@Configuration 
@ComponentScan("com.github.yujiaao.reloading")
@EnableWebMvc
public class AppConfig {  
	@Bean  
    public UrlBasedViewResolver urlBasedViewResolver() {  
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
        resolver.setPrefix("/views/");  
        resolver.setSuffix(".jsp");
        resolver.setCache(false);
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    }

    @Bean
    public Filter filter(){
	    return  new GlobalFilter();
    }


    @Bean
    public ApplicationListener<ServletWebServerInitializedEvent> applicationListener(){
	    return new WebServerStartedListener();
    }

    @Bean
    public ServletContextListener servletContextListener(){
        return new ContextListener();
    }
}  
 