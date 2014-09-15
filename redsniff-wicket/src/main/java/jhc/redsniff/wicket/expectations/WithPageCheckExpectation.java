package jhc.redsniff.wicket.expectations;

import static jhc.redsniff.pageError.NoPageErrorExpectation.noPageErrorExpected;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.wicket.WicketPageExceptionTraceChecker;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class WithPageCheckExpectation{

	public static FindingExpectation<WebElement, CollectionOf<WebElement>, SearchContext>  pageCheckExpectation(){
		return noPageErrorExpected(new WicketPageExceptionTraceChecker());
	}
}
