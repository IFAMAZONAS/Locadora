package br.com.cin.bdd.cliente;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/Cliente.feature", plugin = {"pretty", "html:target/cucumber"})
public class ClienteBDDRunner {	
}
