package com.github.shevchuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication(
        exclude = {JpaRepositoriesAutoConfiguration.class},
        scanBasePackages={"com.github.shevchuk"})
public class HunkApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(HunkApplication.class, args);

    }

}
