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
package jhc.redsniff.webdriver;

import jhc.redsniff.webdriver.matchers.ElementDisplayedMatcher;

import jhc.redsniff.webdriver.matchers.AttributeMatcher;
import jhc.redsniff.webdriver.matchers.SimpleAttributeMatcher;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public final class WebDriverMatchers {

    private WebDriverMatchers() {
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} is enabled,
     * according to the {@link WebElement#isEnabled()} method
     * @return
     */
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> isEnabled() {
        return jhc.redsniff.webdriver.matchers.EnabledElementMatcher.isEnabled();
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} is disabled
     *  according to the {@link WebElement#isEnabled()} method
     * @return
     */
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> isDisabled() {
        return jhc.redsniff.webdriver.matchers.EnabledElementMatcher.isDisabled();
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} is displayed,
     *  according to the {@link WebElement#isDisplayed()} method
     * @return
     */
    public static Matcher<WebElement> isDisplayed() {
        return ElementDisplayedMatcher.isDisplayed();
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} is not displayed,
     *  according to the {@link WebElement#isDisplayed()} method
     * @return
     */
    public static Matcher<WebElement> isHidden() {
        return ElementDisplayedMatcher.isHidden();
    }
    
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasText(
            org.hamcrest.Matcher<java.lang.String> stringMatcher) {
        return jhc.redsniff.webdriver.matchers.TextMatcher.hasText(stringMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasText(java.lang.String text) {
        return jhc.redsniff.webdriver.matchers.TextMatcher.hasText(text);
    }
    
   

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> isSelected() {
        return jhc.redsniff.webdriver.matchers.ElementSelectedMatcher.isSelected();
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> isUnselected() {
        return jhc.redsniff.webdriver.matchers.ElementSelectedMatcher.isUnselected();
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasSelectedState(boolean selected) {
        return jhc.redsniff.webdriver.matchers.ElementSelectedMatcher.hasSelectedState(selected);
    }
    

    /**
     * Creates a matcher that matches if the examined {@link WebElement} has drop-down options with the given text
     * as sub-elements within it, in order.
     * For example:
     * 
     * <pre>&lt;select name ="detective"&gt;
     * 	&lt;option id="WA"&gt;Wallander&lt;/option&gt;
     * 	&lt;option id="LU"&gt;Lund&lt;/option&gt;
     * 	&lt;option id="MO"&gt;Montalbano&lt;/option&gt;
     * &lt;/select&gt;</pre>
     * <pre>t.assertThatThe(first(dropDown()), hasOptions("Wallander", "Lund", "Montalbano"));</pre>
     */
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasOptions(java.lang.String... optionText) {
        return jhc.redsniff.webdriver.matchers.DropDownHasOptions.hasOptions(optionText);
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} has drop-down options text that
     * match the given String matchers in order
     * For example:
     * 
     * <pre>&lt;select name ="detective"&gt;
     * 	&lt;option id="WA"&gt;Wallander&lt;/option&gt;
     * 	&lt;option id="LU"&gt;Lund&lt;/option&gt;
     * 	&lt;option id="MO"&gt;Montalbano&lt;/option&gt;
     * &lt;/select&gt;</pre>
     * <pre>t.assertThatThe(first(dropDown()), hasOptions(startsWith("Wall"), containsString("nd"), is("Montalbano")));</pre>
     */
    @SafeVarargs
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasOptions(
            org.hamcrest.Matcher<? super java.lang.String>... optionTextMatchers) {
        return jhc.redsniff.webdriver.matchers.DropDownHasOptions.hasOptions(optionTextMatchers);
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} has drop-down options with the given text
     * as sub-elements within it, in order.
     * For example:
     * 
     * <pre>&lt;select name ="detective"&gt;
     * 	&lt;option id="WA"&gt;Wallander&lt;/option&gt;
     * 	&lt;option id="LU"&gt;Lund&lt;/option&gt;
     * 	&lt;option id="MO"&gt;Montalbano&lt;/option&gt;
     * &lt;/select&gt;</pre>
     * <pre>t.assertThatThe(first(dropDown()), hasOptions("Lund", "Montalbano", "Wallander"));</pre>
     */
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasOptionsInAnyOrder(
            java.lang.String... optionText) {
        return jhc.redsniff.webdriver.matchers.DropDownHasOptions.hasOptionsInAnyOrder(optionText);
    }

    /**
     * Creates a matcher that matches if the examined {@link WebElement} has drop-down options text that
     * match the given String matchers in any order
     * For example:
     * 
     * <pre>&lt;select name ="detective"&gt;
     * 	&lt;option id="WA"&gt;Wallander&lt;/option&gt;
     * 	&lt;option id="LU"&gt;Lund&lt;/option&gt;
     * 	&lt;option id="MO"&gt;Montalbano&lt;/option&gt;
     * &lt;/select&gt;</pre>
     * <pre>t.assertThatThe(first(dropDown()), hasOptions( containsString("nd"), is("Montalbano"), startsWith("Wall")));</pre>
     */
    @SafeVarargs
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasOptionsInAnyOrder(
            org.hamcrest.Matcher<? super java.lang.String>... optionTextMatchers) {
        return jhc.redsniff.webdriver.matchers.DropDownHasOptions.hasOptionsInAnyOrder(optionTextMatchers);
    }

    //String matchers
    /**
     * Creates a matcher for string equality which will report the mismatch using the {@link ComparisonCompactor} taken from Junit
     * @param expected the string to match against
     * <p/>
     * For example:
     * <pre>assertThat("abcde", isString("abce"))</pre>
     * 
     * <pre>-> Expected: "abce"
     * but: was:
     *  'abc[d]e'
     *instead of:
     *  'abc[]e'
     *  </pre>
     */
    public static org.hamcrest.Matcher<java.lang.String> isString(java.lang.String expected) {
        return jhc.redsniff.internal.matchers.StringMatcher.isString(expected);
    }
    
    /**
     * Creates a matcher that matchers the examined String if that String, after trimming, is equal to the supplied string
     * @param trimmed
     * 
     * For example:
     * <pre>assertThat("Lund  \t", trimmedIs("Lund"))</pre>
     */
    public static org.hamcrest.Matcher<java.lang.String> trimmedIs(java.lang.String trimmed) {
        return jhc.redsniff.internal.matchers.TrimmedStringMatcher.trimmedIs(trimmed);
    }

    //Table matchers
    
    
    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell> valueInColumn(
            java.lang.Object columnIdentifier, java.lang.String cellContents) {
        return jhc.redsniff.webdriver.table.matchers.IsTableRowIncluding.valueInColumn(columnIdentifier, cellContents);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell> valueInColumn(
            java.lang.Object columnIdentifier, org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell> cellMatcher) {
        return jhc.redsniff.webdriver.table.matchers.IsTableRowIncluding.valueInColumn(columnIdentifier, cellMatcher);
    }

    @SafeVarargs
    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableRow> includesCells(
            org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell>... included) {
        return jhc.redsniff.webdriver.table.matchers.IsTableRowIncluding.includesCells(included);
    }

    @SafeVarargs
    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableRow> rowIncluding(
            org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell>... included) {
        return jhc.redsniff.webdriver.table.matchers.IsTableRowIncluding.rowIncluding(included);
    }

   

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.Table> hasHeaderRow(
            org.hamcrest.Matcher<jhc.redsniff.html.tables.TableRow> headerMatcher) {
        return jhc.redsniff.webdriver.table.matchers.IsTableWithHeader.hasHeaderRow(headerMatcher);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.Table> hasHeader(
            java.util.List<org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell>> headerRowCells) {
        return jhc.redsniff.webdriver.table.matchers.IsTableWithHeader.hasHeader(headerRowCells);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.Table> hasHeaders(java.lang.Object... headers) {
        return jhc.redsniff.webdriver.table.matchers.IsTableWithHeader.hasHeaders(headers);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableRow> rowConsistingOf(java.lang.Object... fields) {
        return jhc.redsniff.webdriver.table.matchers.IsTableRowConsistingOf.rowConsistingOf(fields);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell> cellWithText(
            org.hamcrest.Matcher<java.lang.String> textMatcher) {
        return jhc.redsniff.webdriver.table.matchers.IsTableCellWithText.cellWithText(textMatcher);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableCell> cellWithText(
            String text) {
        return jhc.redsniff.webdriver.table.matchers.IsTableCellWithText.cellWithText(text);
    }

    @SafeVarargs
    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.Table> hasDataRows(
            org.hamcrest.Matcher<jhc.redsniff.html.tables.TableRow>... dataRowMatchers) {
        return jhc.redsniff.webdriver.table.matchers.IsTableConsistingOfRows.hasDataRows(dataRowMatchers);
    }

   

    /**
     * Matches a table element that uses blah
     * 
     * @param tableMatcher
     * @return
     */
    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> isTableElementThat(
            org.hamcrest.Matcher<jhc.redsniff.html.tables.Table> tableMatcher) {
        return jhc.redsniff.webdriver.table.matchers.TableElementMatcher.isTableElementThat(tableMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasCssClass(
            org.hamcrest.Matcher<java.lang.String> cssClassNameMatcher) {
        return jhc.redsniff.webdriver.matchers.CssClassNameMatcher.hasCssClass(cssClassNameMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasCssClass(java.lang.String cssClass) {
        return jhc.redsniff.webdriver.matchers.CssClassNameMatcher.hasCssClass(cssClass);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasName(java.lang.String value) {
        return jhc.redsniff.webdriver.matchers.NameMatcher.hasName(value);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasTagName(java.lang.String value) {
        return jhc.redsniff.webdriver.matchers.TagNameMatcher.hasTagName(value);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasName(
            org.hamcrest.Matcher<java.lang.String> valueMatcher) {
        return jhc.redsniff.webdriver.matchers.NameMatcher.hasName(valueMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasValue(
            org.hamcrest.Matcher<java.lang.String> valueMatcher) {
        return SimpleAttributeMatcher.hasValue(valueMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasValue(java.lang.String value) {
        return SimpleAttributeMatcher.hasValue(value);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasId(
            org.hamcrest.Matcher<java.lang.String> valueMatcher) {
        return jhc.redsniff.webdriver.matchers.IdMatcher.hasId(valueMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasId(java.lang.String value) {
        return jhc.redsniff.webdriver.matchers.IdMatcher.hasId(value);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasAttribute(java.lang.String attribute,
            org.hamcrest.Matcher<java.lang.String> stringMatcher) {
        return AttributeMatcher.hasAttribute(attribute, stringMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasAttribute(java.lang.String attribute,
            java.lang.String attributeDescription, org.hamcrest.Matcher<java.lang.String> stringMatcher) {
        return AttributeMatcher.hasAttribute(attribute, attributeDescription,
                stringMatcher);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasAttribute(java.lang.String attribute,
                                                                                    java.lang.String attributeDescription, String text) {
        return AttributeMatcher.hasAttribute(attribute, attributeDescription,
                text);
    }

    public static org.hamcrest.Matcher<org.openqa.selenium.WebElement> hasAttribute(java.lang.String attribute,
            java.lang.String text) {
        return AttributeMatcher.hasAttribute(attribute, text);
    }

    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.TableColumn> isColumnIdentifiedBy(
            java.lang.Object identifier) {
        return jhc.redsniff.webdriver.table.matchers.IsTableColumn.isColumnIdentifiedBy(identifier);
    }

    public static org.hamcrest.Matcher<WebElement> hasSubElement(
            jhc.redsniff.core.MFinder<org.openqa.selenium.WebElement, org.openqa.selenium.SearchContext> subElementFinder) {
        return (Matcher<WebElement>)(Matcher)jhc.redsniff.internal.matchers.HasSubElement.hasSubElement(subElementFinder);
    }

    @SafeVarargs
    public static org.hamcrest.Matcher<jhc.redsniff.html.tables.Table> hasDataRowsIncluding(
            org.hamcrest.Matcher<jhc.redsniff.html.tables.TableRow>... dataRowMatchers) {
        return jhc.redsniff.webdriver.table.matchers.IsTableIncludingRows.hasDataRowsIncluding(dataRowMatchers);
    }

}
