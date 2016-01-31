package jhc.redsniff.webdriver;


import jhc.redsniff.core.MFinder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.webdriver.Finders.list;
import static jhc.redsniff.webdriver.Finders.listItem;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasAttribute;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;

public class CreateEventTest {

    @Test
    public void shouldRunInDecentTime() {
        WebDriver driver = new ChromeDriver();
        RedsniffWebDriverTester browser = new RedsniffWebDriverTester(driver);
        browser.goTo("file:///C:/Users/Nic/jhc/createevent/CreateEvent.html");

        final MFinder<WebElement, SearchContext> ownerSelectContainer = list().that(hasAttribute("data-ref", "users-list"));

        browser.inThe(ownerSelectContainer).clickOn(listItem().that(hasText("DART1")));
        driver.quit();
    }
}
