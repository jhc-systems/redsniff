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
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.StringMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class TextMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private final Matcher<String> stringMatcher;

    TextMatcher(Matcher<String> stringMatcher) {
        this.stringMatcher = stringMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has text ");
        stringMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        return matchAndDiagnose(stringMatcher, item.getText(), mismatchDescription, "text ");
    }

    @Factory
    public static Matcher<WebElement> hasText(Matcher<String> stringMatcher) {
        return new TextMatcher(stringMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasText(String text) {
        return new TextMatcher(StringMatcher.isString(text));
    }

}
