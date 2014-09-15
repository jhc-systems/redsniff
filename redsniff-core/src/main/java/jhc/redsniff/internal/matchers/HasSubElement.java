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

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class HasSubElement<E, Q extends Quantity<E>, C> extends CheckAndDiagnoseTogetherMatcher<C> {

    private final Finder<E, Q, C> childFinder;

    public HasSubElement(Finder<E, Q, C> childFinder) {
        this.childFinder = childFinder;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(" contains : {a ")
                .appendDescriptionOf(childFinder).appendText("}");
    }

    @Override
    protected boolean matchesSafely(C candidateElement, Description mismatchDescription) {
        ExpectationCheckResult<E, Q> result = checkerFor(candidateElement).resultOfChecking(expectationOfSome(childFinder));
        boolean matches = result.meetsExpectation();
        if (!matches)
            mismatchDescription
                    .appendText("child elements did not match:\n")
                    .appendDescriptionOf(result);
        return matches;

    }

    @Factory
    public static <E, Q extends Quantity<E>, C> Matcher<C> hasSubElement(Finder<E, Q, C> subElementFinder) {
        return new HasSubElement<E, Q, C>(subElementFinder);
    }
}
