package com.guan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.guan.mapper")
public class DrugOrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrugOrmApplication.class, args);
    }

}
