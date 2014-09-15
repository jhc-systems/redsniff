package jhc.redsniff.webdriver.activity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Duration;

public interface Activity {

    public Duration initialDelay();//so we don't check if busy before it's started..
	public boolean busyOn(WebDriver driver);

	
}
