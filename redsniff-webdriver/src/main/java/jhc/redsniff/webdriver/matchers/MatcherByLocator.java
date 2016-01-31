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
package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.locators.MatcherLocator;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.StringMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public abstract class MatcherByLocator extends CheckAndDiagnoseTogetherMatcher<WebElement>
        implements MatcherLocator<WebElement, SearchContext> {

    private final Matcher<WebElement> wrappedMatcher;
    private final String literalAttribute;

    public MatcherByLocator(Matcher<WebElement> wrappedMatcher) {
        this(wrappedMatcher, null);
    }

    public MatcherByLocator(Matcher<WebElement> wrappedMatcher, String literalAttribute) {
        this.wrappedMatcher = wrappedMatcher;
        this.literalAttribute = literalAttribute;
    }

    protected abstract By getByLocator(String literalAttribute);

    protected boolean usedLiteralArgument() {
        return literalAttribute != null;
    }

    @Override
    public CollectionOf<WebElement> findElementsFrom(SearchContext context) {
        return CollectionOf.collectionOf(getByLocator(literalAttribute).findElements(context));
    }

    @Override
    public boolean canBehaveAsLocator() {
        return usedLiteralArgument();
    }

    @Override
    public void describeTo(Description description) {
        wrappedMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(WebElement actual, Description mismatchDescription) {
        return matchAndDiagnose(wrappedMatcher, actual, mismatchDescription);
    }

    @Override
    public void describeLocatorTo(Description description) {
        description.appendText("element with " + nameOfAttributeUsed() + " ");
        if (usedLiteralArgument())
            description.appendText("\"" + literalAttribute + "\"");
        else
            wrappedMatcher.describeTo(description);
    }

}
