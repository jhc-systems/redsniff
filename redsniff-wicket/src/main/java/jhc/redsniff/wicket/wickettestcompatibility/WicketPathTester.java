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
package jhc.redsniff.wicket.wickettestcompatibility;

import static jhc.redsniff.webdriver.ExtraExpectedConditions.done;
import static jhc.redsniff.webdriver.Finders.*;
import static jhc.redsniff.wicket.WicketFinders.*;
import static jhc.redsniff.webdriver.WebDriverMatchers.*;
import static jhc.redsniff.webdriver.Sugar.into;
import static jhc.redsniff.wicket.ByWicketPath.byWicketPath;
import static jhc.redsniff.wicket.WicketAjaxActivity.wicketAjax;

import java.util.concurrent.TimeUnit;

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.RedsniffQuantityFindingTester;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * 
 * @deprecated - avoid using wicket paths if possible, or use just what is needed rather than full absolute path
 */
public class WicketPathTester {

    private RedsniffWebDriverTester t;

    public WicketPathTester(RedsniffWebDriverTester t) {
        this.t = t;
    }

    /** Asserts visibility of the item found by {@code wicketPath} 
         * @param wicketPath
        * @param expectedText
     * @param t TODO
     * @param path TODO
        */
        public void assertVisibleWicketPath(String path) {
           //t.assertThatThe(wicketItemByPath(path), isDisplayed());
            t.assertPresenceOf(wicketItemByPath(path));
        }

    /**
     * Asserts presence of the item found by {@code wicketPath} and that its text is equal to {@code expectedText} .
     * @param t TODO
     * @param wicketPath
     * @param expectedText
     */
    @Deprecated
    public void assertWicketLabel(String wicketPath, String expectedText) {
       t.assertThatThe(elementFound(byWicketPath(wicketPath)), hasText(expectedText));
    }

    /** Asserts absence of the item found by {@code wicketPath} 
     * @param expectedText
     * @param t TODO
     * @param wicketPath
     */
    public void assertInvisibleWicketPath(String wicketPath) {
        t.assertAbsenceOf(wicketItemByPath(wicketPath));
    }

    /** Asserts presence of the item found by {@code wicketPath} and that it is enabled
    * @param expectedText
     * @param t TODO
     * @param wicketPath
    */
    public void assertEnabledWicketPath(String wicketPath) {
        t.assertThatThe(wicketItemByPath(wicketPath),isEnabled());
    }

    /** Clicks on item found by {@code wicketPath} and waits for wicket ajax
     * @param expectedText
     * @param t TODO
     * @param wicketPath
     */
    public void clickByWicketPathAndWait(String wicketPath) {
        clickByWicketPathWithoutWait(wicketPath);
        t.waitFor(done(wicketAjax()),t.waiting().withTimeout(5, TimeUnit.SECONDS));
    }

    /** Clicks on item found by {@code wicketPath} and waits for wicket ajax
     * @param expectedText
     * @param t TODO
     * @param wicketPath
     */
    public void clickByWicketPathWithoutWait(String wicketPath) {
        t.clickOn(wicketItemByPath(wicketPath));
    }

    /** Get text of item found by {@code wicketPath} 
     * @param wicketPath
     * @param expectedText
     * @param t TODO
     * @param path TODO
     */
    public String getTextByWicketPath(String path) {
        return t.find(only(wicketItemByPath(path))).getText();
    }

    /**
     * Lose focus on an item found by {@code wicketPath}  (eg by typing tab)
     * @param t TODO
     * @param wicketPath
     */
    public void loseFocusOnItemByWicketPathAndWait(String wicketPath) {
       t.tab(only(wicketItemByPath(wicketPath)));        
       t.waitFor(done(wicketAjax()));
    }

    /**
     * assert a dropdown found by {@code wicketPath}  has an option with the given text as a label
     * @param t TODO
     * @param dropDownWicketPath
     * @param choiceText
     */
    public void assertDropDownByWicketPathHasOption(String dropDownWicketPath, String choiceText) {
        t.assertPresenceOf(dropDownOption().that(hasText(choiceText))
                .withinA(wicketItemByPath(dropDownWicketPath)));
    }

    /**
     * assert dropdown found by {@code wicketPath} does not have an option with the given text as label
     * @param t TODO
     * @param dropDownWicketPath
     * @param choiceText
     */
    public void assertDropDownByWicketPathDoesNotHaveOption(String dropDownWicketPath, String choiceText) {
      
    	  t.inThe(only(wicketItemByPath(dropDownWicketPath)))
    	  		.assertAbsenceOf(dropDownOption().that(hasText(choiceText)));        
    }

    public void chooseOnDropDownByWicketPathAndWait(String formPath, String dropDownPath, String choiceText) {
        t.choose(dropDownOption(choiceText).withinA(wicketItemByPath(dropDownPath)).withinA(wicketItemByPath(formPath)));
        t.waitFor(done(wicketAjax()));
    }

    public void submitFormByWicketPath(String wicketPath) {
        t.submit(wicketItemByPath(wicketPath));
    }

    public void setValueByWicketId(String wicketId, String value) {
      t.type(value,into(itemByWicketPath(wicketId)));
      t.waitFor(done(wicketAjax()));
    }

    public void tickByWicketPath(String checkBoxId, boolean value) {
          t.tick(itemByWicketPath(checkBoxId));
    }

    public SFinder<WebElement, SearchContext> itemByWicketPath(String wicketPath) {
        return only(wicketItemByPath(wicketPath));
    }

}
