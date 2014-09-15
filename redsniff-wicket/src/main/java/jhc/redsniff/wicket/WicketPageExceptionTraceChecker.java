package jhc.redsniff.wicket;

import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.webdriver.matchers.TextMatcher.hasText;
import static jhc.redsniff.wicket.WicketFinders.wicketItemByWXPath;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.pageError.PageErrorChecker;

import org.hamcrest.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
public class WicketPageExceptionTraceChecker implements PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> {


	public WicketPageExceptionTraceChecker() {
    }

    @Override
	public  MFinder<WebElement, SearchContext> exceptionTraceFinder() {
		return wicketItemByWXPath("//w{exception}");
	}

	@Override
	public MFinder<WebElement, SearchContext> errorOnPageIndicatorFinder() {
		return elementFound(By.tagName("h1")).that(hasText(isString("Unexpected RuntimeException")));
	}

    @Override
    public void describeExceptionTraceTo(CollectionOf<WebElement> exceptionElement, Description description) {
        description
            .appendDescriptionOf(Describer.describable(exceptionElement));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("wicket error page");
    }
    
    public static PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> wicketPageExceptionChecker(){
        return new WicketPageExceptionTraceChecker();
    }

}
