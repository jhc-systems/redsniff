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
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;

import org.hamcrest.Description;

//TODO NOt yet doing anything useful
@SuppressWarnings("unused")
public class FoundElementsAre<C, E extends C> extends CheckAndDiagnoseTogetherMatcher<E> {

    private final MFinder<E, C> childFinder;
   
    private final MFinder<E, C>[] expectedFindings;

    public FoundElementsAre(MFinder<E, C> childFinder, MFinder<E, C>[] expectedFindings) {
        this.childFinder = childFinder;
        this.expectedFindings = expectedFindings;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("FINDS SAME ELEMENTS");
    }
    // TODO
    @Override
    protected boolean matchesSafely(E candidateElement,
            Description mismatchDescription) {
        ExpectationCheckResult<E, CollectionOf<E>> result = checkerFor((C)candidateElement).resultOfChecking(expectationOfSome(childFinder));
        result.foundQuantity();
        return false;
    }

}
