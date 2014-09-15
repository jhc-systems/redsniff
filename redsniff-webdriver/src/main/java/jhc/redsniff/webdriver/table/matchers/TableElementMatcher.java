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
package jhc.redsniff.webdriver.table.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class TableElementMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private Matcher<Table> tableMatcher;

    TableElementMatcher(Matcher<Table> tableMatcher) {
        this.tableMatcher = tableMatcher;
    }

    @Override
    protected boolean matchesSafely(WebElement element, Description mismatchDescription) {
        return matchAndDiagnose(tableMatcher, Table.fromTableElement(element), mismatchDescription, " table ");
    }

    public void describeTo(Description description) {
        description.appendText(" a table ");
        description.appendDescriptionOf(tableMatcher);
    }

    @Factory
    public static Matcher<WebElement> isTableElementThat(Matcher<Table> tableMatcher) {
        return new TableElementMatcher(tableMatcher);
    }
}