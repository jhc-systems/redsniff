/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jhc.redsniff.webdriver.matchers;


import jhc.redsniff.internal.locators.MatcherLocator;
import jhc.redsniff.internal.matchers.StringMatcher;
import jhc.redsniff.internal.util.MatcherToLiteralConverter;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static jhc.redsniff.internal.util.MatcherToLiteralConverter.literalStringFrom;
import static jhc.redsniff.webdriver.matchers.SimpleAttributeMatcher.hasSimpleAttribute;

public class IdMatcher extends MatcherByLocator {
    private static int specificity = Specifities.specifityOf(IdMatcher.class);

    public IdMatcher(Matcher<String> idMatcher) {
        super(wrapMatcher(idMatcher));
    }

    public IdMatcher(String id) {
        super(wrapMatcher(StringMatcher.isString(id)), id);
    }

    private static Matcher<WebElement> wrapMatcher(Matcher<String> idMatcher) {
        return hasSimpleAttribute("id", "id", idMatcher);
    }

    @Override
    public By getByLocator(String literalId) {
        return By.id(literalId);
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasId(String value) {
        return new IdMatcher(value);
    }

    @Factory
    public static Matcher<WebElement> hasId(Matcher<String> valueMatcher) {
        String literal = literalStringFrom(valueMatcher);
        return literal == null
                ? new IdMatcher(valueMatcher)
                : hasId(literal);
    }

    @Override
    public String nameOfAttributeUsed() {
        return "id";
    }

    @Override
    public int specifity() {
        return specificity;
    }

}