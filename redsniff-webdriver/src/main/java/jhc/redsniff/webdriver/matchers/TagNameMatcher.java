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
import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import jhc.redsniff.internal.locators.MatcherLocator;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class TagNameMatcher extends MatcherByLocator {

    private final String tagName;
    private static final int specificity = Specifities.specifityOf(TagNameMatcher.class);
    public TagNameMatcher(String tagName) {
        super(wrapMatcher(tagName), tagName);
        this.tagName = tagName;
    }

    @Override
    public String nameOfAttributeUsed() {
        return "tagName";
    }

    @Override
    public By getByLocator(String literalTagname) {
        return By.tagName(literalTagname);
    }


    private static Matcher<WebElement> wrapMatcher(final String tagName) {
        return new TypeSafeDiagnosingMatcher<WebElement>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("has tagname \"" + tagName + "\"");
            }

            @Override
            protected boolean matchesSafely(WebElement actualItem, Description mismatchDescription) {
                return matchAndDiagnose(isString(tagName),
                        actualItem.getTagName(),
                        mismatchDescription,
                        "tagName ");
            }
        };
    }

    @Override
    public void describeLocatorTo(Description description) {
        if (usedLiteralArgument())
            description.appendText(tagName);
        else
            super.describeLocatorTo(description);
    }

    @Override
    public int specifity() {
    	return specificity;
    }
    
    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasTagName(String value) {
        return new TagNameMatcher(value);
    }

}
