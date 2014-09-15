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

import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class CssRenderingMatcher extends ElementStateMatcher<String> {

    private final String cssAttributeName;

    public CssRenderingMatcher(String cssAttributeName, Matcher<String> subMatcher) {
        super(subMatcher, "css rendering of '" + cssAttributeName + "'", "css rendering of '" + cssAttributeName + "'");
        this.cssAttributeName = cssAttributeName;
    }

    @Override
    protected String stateOf(WebElement actual) {
        return actual.getCssValue(cssAttributeName);
    }

    @Factory
    public static Matcher<WebElement> renderedWith(String cssAttributeName, Matcher<String> cssAttributeMatcher) {
        return new CssRenderingMatcher(cssAttributeName, cssAttributeMatcher);
    }

    @Factory
    public static Matcher<WebElement> renderedWith(String cssAttributeName, String cssAttributeValue) {
        return new CssRenderingMatcher(cssAttributeName, is(isString(cssAttributeValue)));
    }

}
