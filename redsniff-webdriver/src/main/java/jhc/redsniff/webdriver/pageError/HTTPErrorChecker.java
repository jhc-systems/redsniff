package jhc.redsniff.webdriver.pageError;

import static jhc.redsniff.core.Describer.describable;
import static jhc.redsniff.webdriver.Finders.tag;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import static org.hamcrest.Matchers.startsWith;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.pageError.PageErrorChecker;

import org.hamcrest.Description;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class HTTPErrorChecker implements PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> {

    @Override
    public void describeTo(Description description) {
        description.appendText("http error");
    }

    @Override
    public Finder<WebElement, CollectionOf<WebElement>, SearchContext> exceptionTraceFinder() {
        return tag("pre");
    }

    @Override
    public Finder<WebElement, CollectionOf<WebElement>, SearchContext> errorOnPageIndicatorFinder() {
       return tag("h2").that(hasText(startsWith("HTTP ERROR")));
    }

    @Override
    public void describeExceptionTraceTo(CollectionOf<WebElement> exceptionElements, Description description) {
        description.appendDescriptionOf(describable(exceptionElements));
    }

    public static PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> http500PageChecker(){
        return new HTTPErrorChecker();
    }
}
