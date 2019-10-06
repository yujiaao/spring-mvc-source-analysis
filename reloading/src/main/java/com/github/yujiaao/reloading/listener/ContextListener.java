package com.github.yujiaao.reloading.listener;

import com.github.yujiaao.reloading.Application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContextListener::contextInitialized");
        Application.Holder.getInstance().load();
    }
}
