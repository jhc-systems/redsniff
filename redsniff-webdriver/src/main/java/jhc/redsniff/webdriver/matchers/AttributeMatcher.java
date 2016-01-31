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
import jhc.redsniff.internal.util.MatcherToLiteralConverter;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static jhc.redsniff.internal.matchers.StringMatcher.isString;

/**
 *
 */
public final class AttributeMatcher extends MatcherByLocator {
    private static final int specificity = Specifities.specifityOf(AttributeMatcher.class);

    private final String attribute;


    private AttributeMatcher(String attribute, String attributeDescription, Matcher<String> valueMatcher) {
        super(SimpleAttributeMatcher.hasSimpleAttribute(attribute, attributeDescription, valueMatcher));
        this.attribute = attribute;
    }

    private AttributeMatcher(String attribute, String attributeDescription, String literalValue) {
        super(SimpleAttributeMatcher.hasSimpleAttribute(attribute, attributeDescription, isString(literalValue)), literalValue);
        ;
        this.attribute = attribute;
    }

    @Override
    public By getByLocator(String literalValue) {
        return By.cssSelector("[" + attribute + "=" + literalValue + "]");
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasAttribute(String attribute, String literalValue) {
        return hasAttribute(attribute, descriptionOfAttribute(attribute), literalValue);
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasAttribute(String attribute, String attributeDescription, Matcher<String> valueMatcher) {
        String literal = MatcherToLiteralConverter.literalStringFrom(valueMatcher);
        return literal == null
                ? new AttributeMatcher(attribute, attributeDescription, valueMatcher)
                : hasAttribute(attribute, attributeDescription, literal);
    }

    @Factory
    public static Matcher<WebElement> hasAttribute(String attribute, Matcher<String> valueMatcher) {
        return new AttributeMatcher(attribute, descriptionOfAttribute(attribute), valueMatcher);
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasAttribute(String attribute, String attributeDescription, String literalValue) {
        return new AttributeMatcher(attribute, attributeDescription, literalValue);
    }

    private static String descriptionOfAttribute(String attribute) {
        return "attribute '" + attribute + "'";
    }

    @Override
    public String nameOfAttributeUsed() {
        return "attribute '" + attribute + "'";
    }

    @Override
    public int specifity() {
        return specificity;
    }

}
