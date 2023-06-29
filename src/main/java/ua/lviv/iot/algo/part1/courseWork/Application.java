package ua.lviv.iot.algo.part1.courseWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

    public static void main(final String ... args) {
        SpringApplication.run(Application.class, args);
    }

}
