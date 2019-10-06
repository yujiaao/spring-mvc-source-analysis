package com.github.yujiaao.reloading.listener;

import com.github.yujiaao.reloading.Application;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

public class WebServerStartedListener implements ApplicationListener<ServletWebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        System.out.println("ServletWebServerInitializedEvent");
    }
}
