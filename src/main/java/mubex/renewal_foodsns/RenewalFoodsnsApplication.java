package mubex.renewal_foodsns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class RenewalFoodsnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RenewalFoodsnsApplication.class, args);
    }

}
