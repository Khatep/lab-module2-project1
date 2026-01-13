package org.kaspi.labmodule2project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "org.kaspi.labmodule2project1.clients")
public class LabModule2Project1Application {

    public static void main(String[] args) {
        SpringApplication.run(LabModule2Project1Application.class, args);
    }

}
