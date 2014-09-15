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

import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static jhc.redsniff.webdriver.WebDriverMatchers.trimmedIs;

import java.util.List;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import com.google.common.base.Function;

public class IsTableCellWithText extends CheckAndDiagnoseTogetherMatcher<TableCell> {

    private final Matcher<String> textMatcher;

    public IsTableCellWithText(Matcher<String> textMatcher) {
        this.textMatcher = textMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(textMatcher);
    }

    @Override
    public boolean matchesSafely(TableCell cell, Description mismatchDescription) {
        return matchAndDiagnose(textMatcher, cell.getText(), mismatchDescription);
    }

    @Factory
    public static Matcher<TableCell> cellWithText(Matcher<String> textMatcher) {
        return new IsTableCellWithText(textMatcher);
    }

    @Factory
    public static Matcher<TableCell> cellWithText(String text) {
        return new IsTableCellWithText(isString(text));
    }

    @SuppressWarnings("unchecked")
    public static final Function<Object, Matcher<TableCell>> TO_CELL_MATCHER = new Function<Object, Matcher<TableCell>>() {
        public Matcher<TableCell> apply(Object input) {
            if (input instanceof Matcher)
                return (Matcher<TableCell>) input;
            else if (input instanceof String)
                return cellWithText((String) input);
            else
                throw new AssertionError("Cannot create a table cell matcher for input of type "
                        + input.getClass().getName() + "value:" + input.toString());
        }
    };

    /**
     * Gives a list of table cell matchers for each String field to match
     * ignoring trailing whitespace
     * 
     * @param fields
     * @return
     */
    @Factory
    public static List<Matcher<TableCell>> trimmed(String... fields) {
        return transform(asList(fields),
                new Function<String, Matcher<TableCell>>() {
                    public Matcher<TableCell> apply(String input) {
                        return cellWithText(trimmedIs(input));
                    }
                });
    }

}