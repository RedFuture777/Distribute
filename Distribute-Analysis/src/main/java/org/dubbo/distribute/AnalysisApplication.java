package org.dubbo.distribute;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class AnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalysisApplication.class, args);
    }
}
