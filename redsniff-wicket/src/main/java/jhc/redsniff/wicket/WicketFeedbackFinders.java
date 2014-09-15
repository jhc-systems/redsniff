package jhc.redsniff.wicket;

import static jhc.redsniff.core.DescribedAs.describedAs;
import static jhc.redsniff.internal.finders.LocatorFinder.finderForLocator;
import static jhc.redsniff.webdriver.Finders.$;
import static jhc.redsniff.webdriver.finders.ByFinder.locatorFor;
import static jhc.redsniff.webdriver.matchers.TextMatcher.hasText;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.webdriver.Finders;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class WicketFeedbackFinders {
    private WicketFeedbackFinders() {
    }

    @Factory
    public static MFinder<WebElement, SearchContext> feedbackError(Matcher<String> errorMessageMatcher) {
        return wicketFeedbackError().that(hasText(errorMessageMatcher));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> feedbackError(String errorMessage) {
        return wicketFeedbackError().that(hasText(errorMessage));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> anyFeedbackError() {
        return wicketFeedbackError();
    }

    @Factory
    public static MFinder<WebElement, SearchContext> wicketFeedbackError() {
        return finderForLocator(
                describedAs("wicket feedback error", 
                		locatorFor(By.cssSelector("span.feedbackPanelERROR"))))
                .withinA(feedbackContainer());
    }

    public static MFinder<WebElement, SearchContext> feedbackContainer() {
        return describedAs("wicket feedback container", $(".feedbackContainer , .feedbackPanel"));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> feedbackWarning(Matcher<String> warningMessageMatcher) {
        return wicketFeedbackWarning().that(hasText(warningMessageMatcher));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> feedbackWarning(String warningMessage) {
        return wicketFeedbackWarning().that(hasText(warningMessage));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> anyFeedbackWarning() {
        return wicketFeedbackWarning();
    }

    @Factory
    public static MFinder<WebElement, SearchContext> wicketFeedbackWarning() {
        return finderForLocator(describedAs("wicket feedback warning", 
        		locatorFor(By.cssSelector("span.feedbackPanelWARNING"))));
    }

}
