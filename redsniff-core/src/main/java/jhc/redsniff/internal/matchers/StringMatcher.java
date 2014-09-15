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
package jhc.redsniff.internal.matchers;


import jhc.redsniff.internal.util.StringDiffFormatter;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Diagnosing String Matcher which uses Comparison Compactor from junit
 * 
 */
public class StringMatcher extends CheckAndDiagnoseTogetherMatcher<String> {

    private final String expected;

    public StringMatcher(String string) {
        this.expected = string;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("\"" + expected + "\"");
    }

    @Override
    protected boolean matchesSafely(String actual, Description mismatchDescription) {
        boolean matches = Matchers.is(expected).matches(actual);
        if (!matches) {
        	String diff = new StringDiffFormatter().formatDifference(expected, actual, "was:\n   '%1$s'\ninstead of:\n   '%2$s'");
        	mismatchDescription.appendText(diff);
		}
        return matches;
    }

    @Factory
    public static Matcher<String> isString(String expected) {
        return new StringMatcher(expected);
    }
}
