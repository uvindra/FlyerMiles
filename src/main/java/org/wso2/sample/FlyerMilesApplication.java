package org.wso2.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.wso2.sample"})
public class FlyerMilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlyerMilesApplication.class, args);
    }
}
