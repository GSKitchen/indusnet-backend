package in.gskitchen.indusnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class IndusnetApplication {

	@EnableWebSecurity
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class SecurityConfig extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception{
			httpSecurity.csrf().disable();
		}
	}
    /** Main method to start Spring boot application */
	public static void main(String[] args) {
		SpringApplication.run(IndusnetApplication.class, args);
	}

}
