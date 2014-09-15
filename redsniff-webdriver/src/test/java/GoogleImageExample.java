import static jhc.redsniff.webdriver.Finders.button;
import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.Finders.fourth;
import static jhc.redsniff.webdriver.Finders.image;
import static jhc.redsniff.webdriver.Finders.only;
import static jhc.redsniff.webdriver.Finders.textbox;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasCssClass;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

@Ignore
public class GoogleImageExample {

	
	
	@Test
	public void findsGoogleImage() throws Exception {
		RedsniffWebDriverTester t = new RedsniffWebDriverTester(new ChromeDriver());
		t.goTo("https://images.google.com/");
		t.type("Nic Infante", textbox().that(hasName("q")));
		t.clickOn(only(button()));
		t.waitFor(div().that(hasText("Images")));
		t.clickOn(fourth(image().that(hasCssClass("rg_i"))));
	}
}
