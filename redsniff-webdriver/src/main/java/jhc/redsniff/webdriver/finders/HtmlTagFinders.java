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
package jhc.redsniff.webdriver.finders;

import jhc.redsniff.core.MFinder;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static jhc.redsniff.core.DescribedAs.describedAs;
import static jhc.redsniff.internal.finders.LocatorFinder.finderForLocator;
import static jhc.redsniff.internal.locators.EitherOrLocatorMatcher.eitherOf;
import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static jhc.redsniff.webdriver.matchers.AttributeMatcher.hasAttribute;
import static jhc.redsniff.webdriver.matchers.IdMatcher.hasId;
import static jhc.redsniff.webdriver.matchers.SimpleAttributeMatcher.hasValue;
import static jhc.redsniff.webdriver.matchers.TagNameMatcher.hasTagName;
import static jhc.redsniff.webdriver.matchers.TextMatcher.hasText;
import static org.hamcrest.Matchers.anyOf;
import static org.openqa.selenium.lift.Matchers.value;

public final class HtmlTagFinders {

    private HtmlTagFinders() {
    }

    @Factory
    public static MFinder<WebElement, SearchContext> tableElement() {
        return simpleFinderForTag("table");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> tableHeaderElement() {
        return simpleFinderForTag("th", "table header element");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> form() {
        return simpleFinderForTag("form");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> dropDown() {
        return simpleFinderForTag("select", "drop-down");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> dropDownOption() {
        return simpleFinderForTag("option", "drop-down option");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> dropDownOption(String optionLabel) {
        return simpleFinderForTag("option", "drop-down option").that(hasText(isString(optionLabel)));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> dropDownOption(Matcher<String> optionLabelMatcher) {
        return simpleFinderForTag("option", "drop-down option").that(hasText(optionLabelMatcher));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> div() {
        return simpleFinderForTag("div");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> listItem() {
        return simpleFinderForTag("li");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> list() {
        return simpleFinderForTag("ul");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> span() {
        return simpleFinderForTag("span");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> textElement() {
        //Could instead do foundBy(By.cssSelector("div, span"))? - then order is preserved
        return finderForLocator(eitherOf(hasTagName("div"), hasTagName("span")));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> link() {
        return simpleFinderForTag("a", "link");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> link(java.lang.String anchorText) {
        return link().that(hasText(isString(anchorText)));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> image() {
        return simpleFinderForTag("img", "image");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> textbox() {
        return describedAs("textbox", inputFinderForType("text"));
    }

    public static MFinder<WebElement, SearchContext> textarea() {
        return simpleFinderForTag("textarea");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> passwordField() {
        return inputFinderForType("password");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> checkbox() {
        return inputFinderForType("checkbox");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> imageButton() {
        return inputFinderForType("image");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> imageButton(String label) {
        return imageButton().that(value((label)));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> radioButton() {
        return inputFinderForType("radio");
    }

    @Factory
    public static MFinder<WebElement, SearchContext> radioButton(String id) {
        return radioButton().that(hasId(id));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> submitButton() {
        return inputFinderForType("submit");
    }

    public static MFinder<WebElement, SearchContext> newButtonTag() {
        return finderForLocator(hasTagName("button"));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> submitButton(String label) {
        return submitButton().that(hasValue(label));
    }


    @Factory
    public static MFinder<WebElement, SearchContext> button() {
        return inputThatHasType(anyOf(isString("button"), isString("submit")));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> button(String label) {
        return button().that(hasValue(label));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> inputFinderForType(String type) {
        return inputThatHasType(isString(type));
    }

    @Factory
    public static MFinder<WebElement, SearchContext> inputThatHasType(Matcher<String> typeMatcher) {
        return simpleFinderForTag("input").that(hasAttribute("type", "type", typeMatcher));
    }

    public static MFinder<WebElement, SearchContext> simpleFinderForTag(String tagName) {
        return simpleFinderForTag(tagName, tagName);
    }

    public static MFinder<WebElement, SearchContext> simpleFinderForTag(final String tagName, final String tagDescription) {
        return finderForLocator(describedAs(tagDescription, hasTagName(tagName)));

    }

    public static MFinder<WebElement, SearchContext> body() {
        return simpleFinderForTag("body");
    }

    public static MFinder<WebElement, SearchContext> iFrame() {
        return simpleFinderForTag("iframe");
    }


}
