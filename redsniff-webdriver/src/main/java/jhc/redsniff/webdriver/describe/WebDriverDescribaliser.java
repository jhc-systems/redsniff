package jhc.redsniff.webdriver.describe;


import jhc.redsniff.internal.describe.Describaliser;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverDescribaliser implements Describaliser<WebDriver>{

	@Override
	public SelfDescribing describable(final WebDriver driver) {
		return new SelfDescribing() {
		
			@Override
			public void describeTo(Description description) {
				new WebElementDescribaliser().describable(rootElementOfPage(driver)).describeTo(description);
			}

			private WebElement rootElementOfPage(final WebDriver driver) {
				return driver.findElement(By.tagName("html"));
			}
		};
	}
}