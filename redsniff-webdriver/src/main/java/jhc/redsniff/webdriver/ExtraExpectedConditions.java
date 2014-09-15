package jhc.redsniff.webdriver;

import static jhc.redsniff.core.Describer.asString;
import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.core.FindingExpectations.expectationOf;
import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;

import java.io.File;
import java.util.Collection;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.predicates.NoItems;
import jhc.redsniff.webdriver.activity.Activity;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 // The ones using finders and matchers still needed somewhere though 
 *  Extra Canned {@link ExpectedCondition}s which are generally useful within webdriver tests 
 */
public final class ExtraExpectedConditions {

    private ExtraExpectedConditions(){}
	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page and satisfies the supplied {@link Matcher}
	 * @param locator
	 * @param matcher
	 * @return
	 */
	public static ExpectedCondition<Boolean> elementsLocated(final By locator,final Matcher<Iterable<WebElement>> matcher){
		return new ExpectedCondition<Boolean>(){
			public Boolean apply(WebDriver input) {
				return matcher.matches(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			}
			@Override
			public String toString() {
				return new StringDescription()
				.appendText("Element located by " + locator + " is ")
				.appendDescriptionOf(matcher).toString();
			}
		};
	}

	/**
	 * An expectation for checking that an element, known to be present on the DOM
	 * of a page, is visible. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0.
	 *
	 * @param element the WebElement
	 * @return the (same) WebElement once it is visible
	 */
	public static ExpectedCondition<WebElement> visibilityOf(final WebElement element) {
		return new ExpectedCondition<WebElement>() {

			public String toString() {
				return "visibility of " + asString(element); 
			}

			@Override
			public WebElement apply(WebDriver input) {
				return ExpectedConditions.visibilityOf(element).apply(input);
			}
		};
	}


	

	/**
	 * Wait until an element is no longer attached to the DOM. The version in {@link ExpectedConditions} is unstable if the element's toString calls any method on the element
	 *
	 * @param element The element to wait for.
	 * @return false is the element is still attached to the DOM, true
	 *         otherwise.
	 */
	public static ExpectedCondition<Boolean> stalenessOf(final WebElement element) {
		final String elementDescription;//need to get the element description first to avoid causing a stale element exception in the description - when it becomes stale too fast
		try{
			elementDescription= asString(element);
		}catch(StaleElementReferenceException e){
			return TRUE_CONDITION;
		}

		return new ExpectedCondition<Boolean>() {
			
			@Override
			public String toString() {
				return "Element to become 'stale':" + elementDescription;
			}

			@Override
			public Boolean apply(WebDriver input) {
				return ExpectedConditions.stalenessOf(element).apply(input);
			}
		};
	}

	/**
	 * Wait until an element is no longer attached to the DOM.
	 *
	 * @param element The element to wait for.
	 * @return false is the element is still attached to the DOM, true
	 *         otherwise.
	   //TODO - add descriptions of elements expected to be stale
	 */
	public static ExpectedCondition<Boolean> stalenessOfAnyOf(final Collection<WebElement> elements) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				for(WebElement element:elements)
					if(ExpectedConditions.stalenessOf(element).apply(input))
						return true;
				return false;
			}

			@Override
			public String toString() {
				return "Staleness of any of a list of elements (description TBA)";  
			}

		};
	}	  

	
	public static ExpectedCondition<Boolean> visibilityOfAny(final MFinder<WebElement,SearchContext> finder){
		return new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				Collection<WebElement> foundElements = checkerFor((SearchContext) driver).thatWhichIsFoundBy(expectationOfSome(finder));
				//TODO - wrap in a that() matcher so don't need the exception check here - 
				//although care needed for the invisibility version - does stale mean can assume invisible? not sure
				if(foundElements.isEmpty())
					return false;
				else {
					for(WebElement element:foundElements){
						try{
							if(element.isDisplayed())
								return true;
						}catch(StaleElementReferenceException e){ // skip - implies not displayed
						} 
					}
					return false;
				}
			}

			@Override
			public String toString() {
				return newDescription().appendDescriptionOf(finder).appendText(" to be visible").toString();
			}
		};
	}
	

	public static ExpectedCondition<Boolean> invisibilityOf(final MFinder<WebElement, SearchContext> finder){
		return new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				Collection<WebElement> foundElements = checkerFor((SearchContext) driver).thatWhichIsFoundBy(expectationOfSome(finder));
				if(foundElements.isEmpty())
					return true;
				else {
					for(WebElement element:foundElements){
						try{
							if(element.isDisplayed())
								return false;
						}catch(StaleElementReferenceException e){} // if stale this implies element is not displayed so we can skip over it
					}
					return true;
				}
			}

			@Override
			public String toString() {
				return newDescription().appendDescriptionOf(finder).appendText(" to be invisible or not present").toString();
			}
		};
	}

	/**
	 * 
	 * @deprecated - use FindingExpectations#presenceOf
	 * @param finder
	 * @return
	 */
	public static ExpectedCondition<Collection<WebElement>> presenceOf(final MFinder<WebElement, SearchContext> finder){
		return new ExpectedCondition<Collection<WebElement>>(){
			@Override
			public String toString() {
				return new StringDescription().appendDescriptionOf(finder).toString();
			}

			@Override
			public Collection<WebElement> apply(WebDriver driver) {
				ExpectationCheckResult<WebElement, CollectionOf<WebElement>> result = checkerFor((SearchContext) driver).resultOfChecking(expectationOfSome(finder));
				if(result.meetsExpectation())
					return result.foundQuantity().get();
				else
					return null;
			}
		};
	}

	@Deprecated
	/**
	 * @deprecated - use FindingExpectations#presenceOf
	 * @param finder
	 * @return
	 */
	public static ExpectedCondition<WebElement> presenceOfA(final MFinder<WebElement,SearchContext> finder){
		return new ExpectedCondition<WebElement>(){
			@Override
			public String toString() {
				return new StringDescription().appendDescriptionOf(finder).toString();
			}

			@Override
			public WebElement apply(WebDriver driver) {
				ExpectationCheckResult<WebElement, CollectionOf<WebElement>> result = checkerFor((SearchContext) driver).resultOfChecking(expectationOfSome(finder));
				if(result.meetsExpectation())
					return result.foundQuantity().iterator().next();
				else
					return null;
			}
		};
	}

	
	public static ExpectedCondition<File> fileToHaveDownloaded(final File file) {
		return new ExpectedCondition<File>() {
			@Override
			public File apply(WebDriver driver) {
				if(file.exists())
					return file;
				else
					return null;
			}
		};
	}

	public static ExpectedCondition<Boolean> absenceOf(final MFinder<WebElement,SearchContext> finder){
		return new ExpectedCondition<Boolean>(){
			@Override
			public String toString() {
				return new StringDescription().appendText("absence of ").appendDescriptionOf(finder).toString();
			}

			@Override
			public Boolean apply(WebDriver driver) {
				try {
				return checkerFor((SearchContext) driver).isMet(expectationOf(NoItems.<WebElement>noElements(), finder));
				}catch(StaleElementReferenceException e){
					return false;
				}
			}

		};
	}
	
	public static ExpectedCondition<Boolean> done(final Activity activity){
		return new DelayingExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
			    
				return !activity.busyOn(driver);
			}

			@Override
			public String toString() {
				return activity.toString() + " to be done";
			}

            @Override
            public Duration initialDelay() {
                return activity.initialDelay();
            }
			
		};
	}

	private static final ExpectedCondition<Boolean> TRUE_CONDITION= new ExpectedCondition<Boolean>() {
		public Boolean apply(WebDriver input) {
			return true;
		}
	};

}
