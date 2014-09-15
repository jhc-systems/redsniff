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

import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.webdriver.matchers.TextMatcher.hasText;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.pageError.PageErrorChecker;

import org.hamcrest.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


public class WicketPageExpiredChecker implements PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> {

    @Override
    public void describeTo(Description description) {
        description.appendText("wicket page expired page");
    }

    @Override
    public Finder<WebElement, CollectionOf<WebElement>, SearchContext> exceptionTraceFinder() {
        return elementFound(By.tagName("body"));
    }

    @Override
    public Finder<WebElement, CollectionOf<WebElement>, SearchContext> errorOnPageIndicatorFinder() {
        return elementFound(By.tagName("h1")).that(hasText(isString("Page Expired")));
    }

    @Override
    public void describeExceptionTraceTo(CollectionOf<WebElement> exceptionElements, Description description) {
        description
        .appendDescriptionOf(Describer.describable(exceptionElements));
    }
    
    public static PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext> wicketPageExpiredChecker(){
        return new WicketPageExpiredChecker();
    }

}
