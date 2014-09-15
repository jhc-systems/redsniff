/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
/*******************************************************************************
 * Concept of Finders and inspiration for whole library from the "Selenium LiFT-style API"
 *  https://code.google.com/p/selenium/wiki/LiftStyleApi
 * created by Robert Chatley (@rchatley) and included in Selenium WebDriver java distributions 
 * in org.openqa.selenium.lift package
 * 
 * This class has similar methods and role to org.openqa.selenium.lift.WebDriverTestContext
 * 
 */
package jhc.redsniff.webdriver;

import static jhc.redsniff.core.Describer.asString;
import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.core.Describer.registerDescribaliser;
import static jhc.redsniff.core.FindingExpectations.presenceOf;
import static jhc.redsniff.pageError.NoPageErrorExpectation.noPageErrorExpected;
import static jhc.redsniff.webdriver.Finders.body;
import static jhc.redsniff.webdriver.Finders.only;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.util.Collections;
import java.util.List;

import jhc.redsniff.action.ActionDriver;
import jhc.redsniff.action.Controller;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.core.RedsniffQuantityFindingTester;
import jhc.redsniff.internal.core.TypedQuantity;
import jhc.redsniff.internal.expectations.DefaultExpectationChecker;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.expectations.ExpectationChecker;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.pageError.PageErrorChecker;
import jhc.redsniff.webdriver.describe.WebDriverDescribaliser;
import jhc.redsniff.webdriver.describe.WebElementDescribaliser;
import jhc.redsniff.webdriver.download.DownloadFactory;
import jhc.redsniff.webdriver.download.FileDownloader;

import org.hamcrest.Matcher;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedsniffWebDriverTester extends RedsniffQuantityFindingTester<WebElement, SearchContext> {

	private static final long DEFAULT_TIMEOUT_SECS = Long.valueOf(System.getProperty("selenium.defaultTimeout","10"));
	private WebDriver driver;
	private FileDownloader fileDownloader; 
	
    @SuppressWarnings({ "unchecked" })
	protected DefaultExpectationChecker<WebElement, CollectionOf<WebElement>, SearchContext> defaultingChecker(){
    	return (DefaultExpectationChecker<WebElement, CollectionOf<WebElement>, SearchContext>)this.checker;
    }
	
	public RedsniffWebDriverTester(WebDriver driver) {  
		this(driver,driver);
	}
	
	public RedsniffWebDriverTester(WebDriver driver, SearchContext context){
		this(new SeleniumController(driver), defaultExpectationChecker(context));
		this.driver=driver;
		this.fileDownloader=new FileDownloader(driver);
	}

    protected RedsniffWebDriverTester(Controller<WebElement> controller, DefaultExpectationChecker<WebElement, CollectionOf<WebElement>, SearchContext> checker) {
        super(controller, checker);
        addPreChecks();
        addUltimateCauseChecks();
    }

    /**
     * The wrapped {@link WebDriver} , for use if need to switch to WebDriver API
     * @return
     */
    public WebDriver getDriver(){
    	return driver;
    }
    
    /**
     * Create a new tester rooted on a particular {@link WebElement} (that isn't expected to go stale)
     * @param context
     * @return
     */
    public RedsniffWebDriverTester newTesterFrom(SearchContext context){
		return new RedsniffWebDriverTester(driver, context);
	}
    
    /**
     * Wait until the supplied {@link FindingExpectation} becomes satisfied, or throw a {@link TimeoutException}
     * , using the supplied {@link FluentWait} 
     * This should only be done using a wait obtained by calling {@link #waiting()} to ensure the correct arguments are passed.
     * 
     * @param expectation
     * @param wait
     * @return
     */
	public <T, E, Q extends TypedQuantity<E, T>> T waitFor(
			 final FindingExpectation<E, Q, SearchContext> expectation,	FluentWait<WebDriver> wait) {
		try {
			return new Waiter(wait).waitFor(expectation);
		}
		catch(FindingExpectationTimeoutException e){
			//do a final check to get the message unoptimized
			ExpectationCheckResult<E, Q> resultOfChecking = checker.resultOfChecking(expectation);
			String reason;
			if(resultOfChecking.meetsExpectation())
				reason = "Expectation met only just after timeout.  At timeout was:\n" + e.getReason();
			else {
				reason = resultOfChecking.toString();
				//if still not found first check there isn't a page error or something
				defaultingChecker().assertNoUltimateCauseWhile(newDescription().appendText("expecting ").appendDescriptionOf(expectation));
			}
			throw new FindingExpectationTimeoutException(e.getOriginalMessage(), reason, e);
		}
	}
	
	
	
	public <T, E, Q extends TypedQuantity<E, T>> T waitFor(
	        final Finder<E, Q, SearchContext> finder) {
	    return waitFor(presenceOf(finder));
	}
	
	public <T, E, Q extends TypedQuantity<E, T>> T waitFor(
            final FindingExpectation<E, Q, SearchContext> expectation) {
        return waitFor(expectation, defaultWait());
    }
	
    public <V> V waitFor(final ExpectedCondition<V> con, FluentWait<WebDriver> wait) {
    	try {
    		return new Waiter(wait).waitFor(con);
    	}
    	catch(TimeoutException e){
    		//first check there wasn't a page error or something
			defaultingChecker().assertNoUltimateCauseWhile(newDescription().appendText("expecting ").appendText(con.toString()));
			throw e;
		}
    }
    
    public <V> V waitFor(final ExpectedCondition<V> con) {
        return waitFor(con, defaultWait());
    }

    public <V> V waitForOrContinueAnyway(ExpectedCondition<V> con, FluentWait<WebDriver> wait) {
    	return new Waiter(wait).waitForOrContinueAnyway(con);
    }
    
    public <V> V waitForOrContinueAnyway(ExpectedCondition<V> con) {
    	return waitForOrContinueAnyway(con, defaultWait());
    }
    
    public <T, E, Q extends TypedQuantity<E, T>> T waitForOrContinueAnyway(FindingExpectation<E, Q, SearchContext> expectation, FluentWait<WebDriver> wait) {
    	return new Waiter(wait).waitForOrContinueAnyway(expectation);
    }

    public <T, E, Q extends TypedQuantity<E, T>> T waitForOrContinueAnyway(FindingExpectation<E, Q, SearchContext> expectation) {
    	return waitForOrContinueAnyway(expectation, defaultWait());
    }
 
    
    public final FluentWait<WebDriver> waiting(){
		return defaultWait();
	}
    

    /**
     * Override to add {@link PageErrorChecker}s that will be run before every assertion/interaction made
     * @return
     */
	protected List<PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext>> preChecks() {
		 return Collections.emptyList();
	}

	

	/**
     * Override to add {@link PageErrorChecker}s that will be run after a failed assertion, to check if some other cause
     * could be responsible
     * <p/>
     * For example, searching for a textbox, and not found, not because the box is missing but
     * because there is a stack trace error on the screen
     * @return
     */
    protected List<PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext>> ultimateCauseChecks() {
        return Collections.emptyList();
    }

    
    @Override
	protected void registerDescribalisers() {
    	registerDescribaliser(WebElement.class, new WebElementDescribaliser());
    	registerDescribaliser(WebDriver.class, new WebDriverDescribaliser());
    }

	private static DefaultExpectationChecker<WebElement, CollectionOf<WebElement>, SearchContext> defaultExpectationChecker(SearchContext context) {
        return DefaultExpectationChecker.defaultExpectationCheckingChecker(context);
    }

	protected RedsniffWebDriverTester(ExpectationChecker<SearchContext> checker,
			Controller<WebElement> controller,
			ActionDriver<WebElement, SearchContext> actionDriver) {
		super(checker, controller, actionDriver);
	}

	protected FluentWait<WebDriver> defaultWait() {
		return new WebDriverWait(driver, DEFAULT_TIMEOUT_SECS);
	}

    public <F, Q extends Quantity<String>> Download<F> getDownload(
            Finder<String, Q, SearchContext> linkFinder,
            DownloadFactory<F> downloadFactory) {
        try {
            String defaultExtension = downloadFactory.getDefaultFileExtension();
            String downloadUrl = find(only(linkFinder));
            File file = fileDownloader.downloadFrom(downloadUrl, defaultExtension);
            return downloadFactory.createDownloadFrom(file);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    
    public <F, Q extends Quantity<String>> 
    void assertDownload(
            Finder<String, Q, SearchContext> linkFinder,
            DownloadFactory<F> downloadFactory,
            Matcher<F> matcher) {
        Download<F> download = null;
        try {
            download = getDownload(linkFinder, downloadFactory);
            String downloadDescription = asString(
                    newDescription()
                    .appendText("download of ")
                    .appendDescriptionOf(downloadFactory)
                    .appendText(" (downloaded from ")
                    .appendDescriptionOf(linkFinder).appendText(")"));
            assertThat(downloadDescription, download.getObject(), matcher);
        } catch (Exception e) {
            throw new AssertionError(e);
        } finally {
            if (download != null)
                download.doneWith();
        }
    } 
        
    public void assertTextPresent(String text) {
    	assertThatThe(body(), hasText(containsString(text)));
    }
    
    public void assertTextAbsent(String text) {
    	assertThatThe(body(), not(hasText(containsString(text))));
    }

    public String getPageSource() {
        return getDriver().getPageSource();
     }

    public void clearSession() {
        getDriver().manage().deleteAllCookies();
    }
    /**
     * Called internally to remove preChecks
     */
    protected void removePreChecks() {
        defaultingChecker().removePreChecks();
    }

    /**
     * Called internally to add preChecks
     */
    protected void addPreChecks() {
    	for(PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> preCheck: preChecks())
            defaultingChecker().addPreCheck(noPageErrorExpected(preCheck));
	}
    
    private void addUltimateCauseChecks() {
        for(PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> ultimateCauseCheck: ultimateCauseChecks())
            defaultingChecker().addUltimateCauseExpectationCheck(noPageErrorExpected(ultimateCauseCheck));
    }
}
