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

import static jhc.redsniff.webdriver.matchers.AttributeMatcher.hasAttribute;
import jhc.redsniff.internal.locators.MatcherLocator;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class NameMatcher extends MatcherByLocator {

    public NameMatcher(Matcher<String> nameMatcher) {
        super(nameMatcher);
    }

    public NameMatcher(String literalAttribute) {
        super(literalAttribute);
    }

    @Override
    public String nameOfAttributeUsed() {
        return "name";
    }

    @Override
    public By getByLocator(String literalName) {
        return By.name(literalName);
    }

    @Override
    public Matcher<WebElement> getWrappedMatcher(Matcher<String> nameMatcher) {
        return hasAttribute("name",  nameMatcher);
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasName(String value) {
        return new NameMatcher(value);
    }

    @Factory
    public static Matcher<WebElement> hasName(Matcher<String> valueMatcher) {
        return new NameMatcher(valueMatcher);
    }

	@Override
	public int specifity() {
		return 95;
	}

}
