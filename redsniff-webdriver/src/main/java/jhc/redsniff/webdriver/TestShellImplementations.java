package jhc.redsniff.webdriver;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByXPath;

/**
 * Implement "not started yet" version of tester, without using mocks
 */
public class TestShellImplementations {
    
    
    public RedsniffWebDriverTester getTester(){
        return new EmptyWebDriverTester();
    }
    
    
    private void error() {
            throw new AssertionError("Test not yet started!");
    }
    
    /**
     * A version of tester representing an uninitialised test state - rather than throw NullPointerExceptions,
     *  throws an error explaining that the test has not yet been started
     *
     */
    private class EmptyWebDriverTester extends RedsniffWebDriverTester {
        public EmptyWebDriverTester() {
            super(new EmptyWebDriver(), new EmptySearchContext());
        }
    }
    
    private class EmptySearchContext implements SearchContext , FindsByXPath{

        @Override
        public List<WebElement> findElements(By by) {
           error();
            return null;
        }

        @Override
        public WebElement findElement(By by) {
           error();
            return null;
        }

        @Override
        public WebElement findElementByXPath(String using) {
            error();
            return null;
        }

        @Override
        public List<WebElement> findElementsByXPath(String using) {
            error();
            return null;
        }
        
    }
   
    
    private class EmptyWebDriver implements WebDriver {
        

        @Override
        public void get(String url) {
            error();
        }

        @Override
        public String getCurrentUrl() {
            error();
            return null;
        }

        @Override
        public String getTitle() {
            error();
            return null;
        }

        @Override
        public List<WebElement> findElements(By by) {
            error();
            return null;
        }

        @Override
        public WebElement findElement(By by) {
            error();
            return null;
        }

        @Override
        public String getPageSource() {
            error();
            return null;
        }

        @Override
        public void close() {
            error();

        }

        @Override
        public void quit() {
            error();

        }

        @Override
        public Set<String> getWindowHandles() {
            error();
            return null;
        }

        @Override
        public String getWindowHandle() {
            error();
            return null;
        }

        @Override
        public TargetLocator switchTo() {
            error();
            return null;
        }

        @Override
        public Navigation navigate() {
            error();
            return null;
        }

        @Override
        public Options manage() {
            error();
            return null;
        }
    }
}
