package com.technopark.iro;

import com.technopark.iro.config.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class})
public class IroApplication {

    public static void main(String[] args) {
        SpringApplication.run(IroApplication.class, args);
    }

}
