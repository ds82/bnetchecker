package de.ds82.java.bnetchecker;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
	}

	@Provides
	WebDriver getWebDriver() throws MalformedURLException {
		
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal");
		Logger logger = Logger.getLogger ("");
		logger.setLevel (Level.OFF);		
		HtmlUnitDriver driver = new HtmlUnitDriver(false);
		return driver;
	}
	
	
	
}
