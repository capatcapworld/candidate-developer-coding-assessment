package dk.et.pm.cdca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
        SpringApplication.run(Application.class, args);
    }

}
