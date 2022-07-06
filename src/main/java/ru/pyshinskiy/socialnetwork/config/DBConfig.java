package ru.pyshinskiy.socialnetwork.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class DBConfig {

    private final DataSource dataSource;

    @PostConstruct
    public void init() {
        System.out.println(dataSource);
    }
}
