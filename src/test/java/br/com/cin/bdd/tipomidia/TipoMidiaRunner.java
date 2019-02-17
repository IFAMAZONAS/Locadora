package br.com.cin.bdd.tipomidia;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/TipoMidia.feature", plugin = {"pretty","html:target/cucumber"})
public class TipoMidiaRunner {

}
