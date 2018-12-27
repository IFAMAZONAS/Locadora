package br.com.cin.locadora;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages="br.com.cin.locadora.*")
@ComponentScan(basePackages= {"br.com.cin.locadora.*"})
@EnableJpaRepositories(basePackages= {"br.com.cin.*"})
@EnableTransactionManagement
public class Configuracao {

	public static void main(String[] args) {
		SpringApplication.run(Configuracao.class, args);
	}
	/**
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/locadora");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		return dataSource;
	}**/

	

}
