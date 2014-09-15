package jhc.redsniff.wicket.pageError;

import static jhc.redsniff.wicket.WicketFeedbackFinders.anyFeedbackError;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.pageError.PageErrorChecker;

import org.hamcrest.Description;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class WicketFeedbackErrorChecker implements
        PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> {
    @Override
    public MFinder<WebElement, SearchContext> exceptionTraceFinder() {
    	return anyFeedbackError();
    }

    @Override
    public MFinder<WebElement, SearchContext> errorOnPageIndicatorFinder() {
    	return anyFeedbackError();
    }
    
    @Override
    public void describeExceptionTraceTo(
    		CollectionOf<WebElement> exceptionElement, Description description) {
            description.appendDescriptionOf(Describer.describable(exceptionElement));
    }

    public boolean validForContext(Object context) {
    	return context instanceof SearchContext;
    }
    public static PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> wicketFeedbackChecker(){
        return new WicketFeedbackErrorChecker();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("feedback error");
    }
}