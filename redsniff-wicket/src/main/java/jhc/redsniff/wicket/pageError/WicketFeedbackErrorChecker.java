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