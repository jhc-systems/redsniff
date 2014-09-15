package jhc.redsniff.webdriver;

import java.lang.reflect.Field;

import jhc.redsniff.action.Controller;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public class SeleniumController implements Controller<WebElement> {

	private final WebDriver driver;

	public SeleniumController(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public void goTo(String url) {
		driver.get(url);
	}

	@Override
	public void click(WebElement element) {
		element.click();
	}
	
	@Override
	public void submit(WebElement element) {
		element.submit();
	}

	@Override
    public void type(String input, WebElement element) {
		element.sendKeys(input);
	}

	@Override
    public void clear(WebElement element) {
		element.clear();
	}

	@Override
    public boolean enabled(WebElement element) {
		return element.isEnabled();
	}

    public WebDriver getDriver() {
    	return driver;
    }

    @Override
    public void tab(WebElement element) {
//XXX shame we have to do it like this..
        if(element instanceof HtmlUnitWebElement)
            tabOnHtmlUnitElement(element);
        else {
            blurUsingJavascript(element);
            //blurByTypingTabKey(element);
        }
    }

    protected void blurByTypingTabKey(WebElement element) {
        new Actions(driver).click(element).sendKeys(Keys.TAB).perform();
    }

    private void blurUsingJavascript(WebElement element) {
        String id = element.getAttribute("id");
        String script = "document.getElementById('"+id+"').blur()";
        ((RemoteWebDriver)driver).executeScript(script);
    }

    private void tabOnHtmlUnitElement(WebElement element) {
        getHtmlUnitElement((HtmlUnitWebElement) element).blur();
    }
    
//XXX
//gets private field
    private HtmlElement getHtmlUnitElement(HtmlUnitWebElement webElement){
        try {
            boolean changed=false;
            Field field = HtmlUnitWebElement.class.getDeclaredField("element");
            if (!field.isAccessible()) {
                
                field.setAccessible(true);
                changed=true;
            }
            
            HtmlElement htmlElement = (HtmlElement)field.get(webElement);
            if(changed)
                field.setAccessible(false);
            return htmlElement;
        } catch (Exception e) {
            throw new Error(e.getMessage(), e);
        }
    }
}
