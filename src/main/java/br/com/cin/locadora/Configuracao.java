package br.com.cin.locadora;

import java.util.EnumSet;


import javax.servlet.DispatcherType;
import javax.sql.DataSource;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.standard.expression.OrExpression;

@SpringBootApplication
@EntityScan(basePackages="br.com.cin.locadora.model")
@ComponentScan(basePackages= {"br.*"})
@EnableJpaRepositories(basePackages= {"br.com.cin.*"})
@EnableTransactionManagement
@EnableWebMvc
public class Configuracao implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Configuracao.class, args);
		System.out.println("Sistema Inicializado ---");
	}	
	
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
	
	
}
