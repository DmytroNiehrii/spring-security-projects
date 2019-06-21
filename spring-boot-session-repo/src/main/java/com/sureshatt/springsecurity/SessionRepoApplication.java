package com.sureshatt.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

@SpringBootApplication
public class SessionRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionRepoApplication.class, args);
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
