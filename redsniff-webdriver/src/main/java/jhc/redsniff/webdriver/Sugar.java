package jhc.redsniff.webdriver;

import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.Quantity;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class Sugar {

    /**
     * Syntactic sugar to use with {@link HamcrestWebDriverTestCase#type(String, Finder<WebElement,
     * SearchContext>)}, e.g. type("cheese", into(textbox())); The into() method simply returns its
     * argument.
     */
    public static <Q extends Quantity<WebElement>> Finder<WebElement,Q, SearchContext> into(Finder<WebElement,Q, SearchContext> input) {
    	return input;
    }

    /**
     * Syntactic sugar to use with {@link HamcrestWebDriverTestCase#type(String, Finder<WebElement,
     * SearchContext>)}, e.g. chooseOption("cheese", on(dropDown())); The on() method simply returns its
     * argument.
     */
    public static MFinder<WebElement, SearchContext> on(MFinder<WebElement, SearchContext> input) {
    	return input;
    }

}
