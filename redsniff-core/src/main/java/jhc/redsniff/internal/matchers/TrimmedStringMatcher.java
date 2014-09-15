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

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.internal.matchers.StringMatcher.isString;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches a string if the target string, when trimmed of whitespace using
 * {@link String#trim()} - is equal to the matched string
 * 
 * @author InfanteN
 * 
 */
public class TrimmedStringMatcher extends CheckAndDiagnoseTogetherMatcher<String> {

    private final String expectedTrimmedString;

    protected TrimmedStringMatcher(String expectedTrimmedString) {
        this.expectedTrimmedString = expectedTrimmedString;
    }

    @Override
    public boolean matchesSafely(String untrimmedActualString, Description mismatchDescription) {
        String actualTrimmedString = untrimmedActualString == null ? null : untrimmedActualString.trim();
        return matchAndDiagnose(isString(expectedTrimmedString), actualTrimmedString, mismatchDescription);
    }

    public void describeTo(Description description) {
        description.appendText("(trimmed) ")
                .appendText(" ")
                .appendValue(expectedTrimmedString);
    }

    @Factory
    public static Matcher<String> trimmedIs(String trimmed) {
        return new TrimmedStringMatcher(trimmed);
    }
}
