package mubex.renewal_foodsns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
public class RenewalFoodsnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenewalFoodsnsApplication.class, args);
	}

}
