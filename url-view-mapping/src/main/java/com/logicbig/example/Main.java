package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Main extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers (ViewControllerRegistry registry) {

        //mapping url to a view
        ViewControllerRegistration r = registry.addViewController("/test");
        r.setViewName("myView");
        //setting status code
        r.setStatusCode(HttpStatus.GONE);

        //mapping another url to a status code
        registry.addStatusController("/test2", HttpStatus.SERVICE_UNAVAILABLE);


        ViewControllerRegistration index = registry.addViewController("/");
        index.setViewName("myindex");
        index.setStatusCode(HttpStatus.ACCEPTED);
    }

    public static void main (String[] args) {
        SpringApplication.run(Main.class, args);
    }
}