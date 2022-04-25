package com.scaffolding.scaffolding;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan("module-service")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//añadiendo esto me quita el error
public class ScaffoldingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScaffoldingApplication.class, args);
    }

}
