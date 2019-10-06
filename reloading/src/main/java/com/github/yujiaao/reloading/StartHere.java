package com.github.yujiaao.reloading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartHere {

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(StartHere.class);
        springApplication.run(args);
    }


}
