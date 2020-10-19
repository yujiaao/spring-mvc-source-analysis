package com.bixuebihui.bootmetrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.hamcrest.MatcherAssert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

@SpringBootApplication
public class Main  {

    public static void main (String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("region", "us-east-1");
    }



}