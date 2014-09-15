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
package jhc.redsniff.wicket;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static jhc.redsniff.core.FindingExpectations.expectationWithPurpose;
import static jhc.redsniff.webdriver.ExtraExpectedConditions.done;
import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.wicket.WicketFeedbackFinders.*;
import static jhc.redsniff.webdriver.Finders.only;
import static jhc.redsniff.wicket.WicketFinders.wicketItemByPath;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasSelectedState;
import static jhc.redsniff.webdriver.Sugar.into;
import static jhc.redsniff.webdriver.pageError.HTTPErrorChecker.http500PageChecker;
import static jhc.redsniff.wicket.WicketAjaxActivity.wicketAjax;
import static jhc.redsniff.wicket.WicketPageExceptionTraceChecker.wicketPageExceptionChecker;
import static jhc.redsniff.wicket.WicketPageExpiredChecker.wicketPageExpiredChecker;
import static jhc.redsniff.wicket.pageError.WicketFeedbackErrorChecker.wicketFeedbackChecker;

import java.util.List;

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.pageError.PageErrorChecker;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;
import jhc.redsniff.wicket.wickettestcompatibility.WicketPathTester;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@SuppressWarnings("deprecation")
public class WicketEnabledTester extends RedsniffWebDriverTester {

    private final WicketPathTester w;

    public WicketEnabledTester(WebDriver driver) {
        super(driver);
        w = new WicketPathTester(this);
    }

    public WicketEnabledTester(WebDriver driver, SearchContext context) {
        super(driver, context);
        w = new WicketPathTester(this);
    }

    @Override
    protected List<PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext>> ultimateCauseChecks() {
        return asList(
                wicketPageExceptionChecker(),
                wicketPageExpiredChecker(),
                http500PageChecker());
    }

    protected List<PageErrorChecker<WebElement, CollectionOf<WebElement>, SearchContext>> preChecks() {
        return asList(
                wicketPageExceptionChecker(),
                wicketPageExpiredChecker(),
                wicketFeedbackChecker());
    }

    public WicketEnabledTester newTesterFrom(SearchContext context) {
        return new WicketEnabledTester(getDriver(), context);
    }

    public void assertNoErrorMessage() {
        assertFeedbackContainerPresent();
        assertAbsenceOf(anyFeedbackError());
    }

    protected void assertFeedbackContainerPresent() throws AssertionError {
        assertThe(expectationWithPurpose(only(feedbackContainer()), 
                "No feedback container on the page in order to check for no errors \n" +
                "override method requiresAdditionalFeedbackPanel() to return true if your component under test does not have its own embedded feedback panel"));

    }
    
  //FIXME
    public void setFeedBackErrorsOk(boolean expected){//XXX should just have a new instance not a state-driven thing
        if(expected)
            removePreChecks();
        else
            addPreChecks();
    }

    public void assertErrorMessages(String... expectedErrorMessages) {
        for (String errorMessage : expectedErrorMessages) {
            assertPresenceOf(feedbackError(errorMessage));
        }
    }

    public void assertNoWarningMessage() {
        assertFeedbackContainerPresent();
        assertAbsenceOf(anyFeedbackWarning());
    }

    public void assertWarningMessages(String... expectedWarningMessages) {
        for (String warningMessage : expectedWarningMessages) {
            assertPresenceOf(feedbackError(warningMessage));
        }
    }

    public String getValueByName(String name) {
        return find(only(itemByName(name))).getAttribute("value");
    }

    public void setValueByNameAndWait(String name, String value) {
        type(value, into(itemByName(name)));
        waitFor(done(wicketAjax()));
    }

    public void loseFocusByName(String name) {
        tab(itemByName(name));
        waitFor(done(wicketAjax()));
    }

    private SFinder<WebElement, SearchContext> itemByName(String name) {
        return only(elementFound(By.name(name)));
    }

    public void tickByName(String checkBoxId, boolean value) {
        WebElement checkBox = find(itemByName(checkBoxId));
        if (checkBox.isSelected() != value)
            tick(itemByName(checkBoxId));
        assertThatThe(itemByName(checkBoxId), hasSelectedState(value));
    }

    /**
     * Gets a new {@link WicketEnabledTester} that is rooted on the item rooted
     * on {@code formWicketPath}- typically a form or section
     * 
     * @param formWicketPath
     * @return
     * @deprecated - avoid using wicket paths if possible, or use just what is
     *             needed rather than full absolute path
     */
    public WicketEnabledTester getTesterRootedOnFormByWicketPath(String formWicketPath) {
        WebElement theForm = find(only(wicketItemByPath(formWicketPath)));
        return newTesterFrom(theForm);
    }

    /**
     * 
     * @deprecated - avoid using wicket paths if possible, or use just what is
     *             needed rather than full absolute path
     * @return
     */
    public WicketPathTester getWicketPathTester() {
        return w;
    }

    public void waitUntilWicketAjaxDone() {
        waitFor(done(wicketAjax()),
                waiting().pollingEvery(100, MILLISECONDS));
    }
}
