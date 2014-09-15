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
