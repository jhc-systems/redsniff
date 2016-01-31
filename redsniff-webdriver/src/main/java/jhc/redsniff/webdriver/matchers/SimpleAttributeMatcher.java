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
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

/**
 * Just the matcher aspect - does not convert to a By.cssSelector for use internally only
 */
public final class SimpleAttributeMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private final Matcher<String> stringMatcher;
    private final String attribute;
    private final String attributeDescription;

    private SimpleAttributeMatcher(String attribute, Matcher<String> stringMatcher) {
        this(attribute, attribute, stringMatcher);
    }

    private SimpleAttributeMatcher(String attribute, String attributeDescription, Matcher<String> stringMatcher) {
        this.stringMatcher = stringMatcher;
        this.attribute = attribute;
        this.attributeDescription = attributeDescription;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has " + (attributeDescription == null ? attribute : attributeDescription) + " ");
        stringMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(WebElement actual, Description mismatchDescription) {

        String attributeValue = actual.getAttribute(attribute);
        if(attributeValue==null){
            mismatchDescription.appendText("did not have a " + (attributeDescription == null ? attribute : attributeDescription + "(" + attribute +")"));
            return false;
        }
            
        return matchAndDiagnose(stringMatcher, attributeValue, mismatchDescription, attribute + " ");
    }

    @Factory
    public static Matcher<WebElement> hasSimpleAttribute(String attribute, Matcher<String> stringMatcher) {
        return new SimpleAttributeMatcher(attribute, stringMatcher);
    }

    public static Matcher<WebElement> hasSimpleAttribute(String attribute, String attributeDescription,
                                                         Matcher<String> stringMatcher) {
        return new SimpleAttributeMatcher(attribute, attributeDescription, stringMatcher);
    }

    public static Matcher<WebElement> hasSimpleAttribute(String attribute, String text) {
        return hasSimpleAttribute(attribute, isString(text));
    }

    public static Matcher<WebElement> hasValue(String value) {
        return hasValue(isString(value));
    }

    public static Matcher<WebElement> hasValue(Matcher<String> valueMatcher) {
        return hasSimpleAttribute("value", valueMatcher);
    }

}
