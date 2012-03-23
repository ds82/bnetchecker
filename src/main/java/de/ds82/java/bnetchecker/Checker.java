package de.ds82.java.bnetchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class Checker 
{
	private final WebDriver driver;
	List<String> ignoredIds = new ArrayList<String>(){{
		add("WoW1::EU");
		add("WoW1::PTR");
		add("addWowTrial");
	}};

	final By byAccount = By.id("accountName");
	final By byPassword = By.id("password");
	final By byLogin = By.cssSelector("#form button[type='submit']");
	
	final By byGames = By.cssSelector("#games-list li");
	final By bySingleGame = By.cssSelector("span.account-link strong a");
	
	final Pattern pattern = Pattern.compile("Pandaria");

	public static void main( String[] args ) {
	
		Injector injector = Guice.createInjector(new Module());
		
		if ((args.length % 2) != 0) System.out.println("use: checker <account> <pw> [<account> <pw>]*");
		else {
			for (int i = 0; i < args.length; i = (i + 2)) {
				Checker checker = injector.getInstance(Checker.class);
				checker.run(args[i], args[i+1]);
			}
			System.out.println("= DONE =");
		}
	}
	
	@Inject
	public Checker(WebDriver driver) {
		this.driver = driver;
	}
	
	public void run(String login, String password) {

		System.out.println("Checking " + login + " ...");
		driver.get("https://eu.battle.net/account/management/");
		
		new WebDriverWait(driver, 10).until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver input) {
				return input.findElement(byAccount);
			}
		});
		
		driver.findElement(byAccount).sendKeys(login);
		driver.findElement(byPassword).sendKeys(password);
		driver.findElement(byLogin).click();
		
		new WebDriverWait(driver, 10).until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver input) {
				return input.findElement(byGames);
			}
		});		
		
		List<WebElement> list = driver.findElements(byGames);
		for(WebElement game : list) {
			if (ignoredIds.contains(game.getAttribute("id"))) continue;
			List<WebElement> links = game.findElements(bySingleGame);
			if (links != null && links.size() == 1) {
				String label = links.get(0).getText();
				System.out.println("found Game: " + label + " ("+game.getAttribute("id")+")");
				
				Matcher matcher = pattern.matcher(label);
				if (matcher.find()) {
					System.out.println("*** FOUND BETA ACCESSS ON "+login+" ***");
				}
				
					
			}
		}
		System.out.println("");
		
		driver.close();
		driver.quit();
	}
	
}
