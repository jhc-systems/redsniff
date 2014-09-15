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
package jhc.redsniff.internal.expectations;

import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.core.FindingExpectations.absenceOf;
import static jhc.redsniff.core.FindingExpectations.expectationOf;
import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.core.FindingExpectations.presenceOf;
import static jhc.redsniff.internal.core.CollectionOf.collectionOf;
import static jhc.redsniff.internal.finders.OnlyFinder.only;
import static jhc.redsniff.util.Util.failWith;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.predicates.SingleItemMatching;

import org.hamcrest.Matcher;

public class ExpectationChecker<C> {
    private final C context;

    public ExpectationChecker(C context) {
        this.context = context;
    }

    public <I, Q extends Quantity<I>> void assertThe(FindingExpectation<I, Q, C> expectation) {
        assertedResultOfChecking(expectation);
    }

    public <I,  Q extends Quantity<I>> Object find(Finder<I, Q, C>finder) {
        return quantityFoundBy(expectationOfSome(finder)).get();
    }

    public <I, Q extends Quantity<I>> Quantity<I> quantityFoundBy(FindingExpectation<I, Q, C> expectation) {
        return thatWhichIsFoundBy(expectation);
    }

    public <I, Q extends Quantity<I>> boolean isSatisfied(FindingExpectation<I, Q, C> expectation) {
        return isMet(expectation);
    }

    public <I, Q extends Quantity<I>> void assertAbsenceOf(Finder<I, Q, C>finder) {
        assertThe(absenceOf(finder));
    }

    public <I, Q extends Quantity<I>> void assertPresenceOf(Finder<I, Q, C> finder) {
        assertThe(presenceOf(finder));
    }

    public <I> void assertPresenceOf(Matcher<Integer> expectedNumberOfElements, MFinder<I, C>finder) {
        assertThe(presenceOf(expectedNumberOfElements, finder));
    }

    public <I, Q extends Quantity<I>> void assertThatThe(Finder<I, Q, C> finder, Matcher<I> matcher) {
        assertThe(expectationOf(SingleItemMatching.<I>singleItemMatches(matcher), only(finder)));
    }

    public <I, Q extends Quantity<I>> boolean isPresent(Finder<I, Q, C>finder) {
        return isMet(expectationOfSome(finder));
    }
    
    //TODO this works now but would be better if it returned a higher level class/interface
    public <C1, E0 extends C1>  WithinEachContextChecker<C1,E0, C> inEachContextFoundBy(MFinder<E0, C> contextFinder){
        return new WithinEachContextChecker<C1,E0, C>(collectionOf(context), contextFinder, null, "each");
    }
    
  //TODO this works now but would be better if it returned a higher level class/interface so inEach().inThe works
    public <C1, E0 extends C1>  WithinSingleContextChecker<C1,E0, C> inSingleContextFoundBy(SFinder<E0, C> contextFinder){
        return new WithinSingleContextChecker<C1,E0, C>(this, contextFinder, null, " ");
    }
    
    private <E, Q extends Quantity<E>> 
    ExpectationCheckResult<E, Q> assertedResultOfChecking(FindingExpectation<E, Q, C> expectation) {
        ExpectationCheckResult<E, Q> result = resultOfChecking(expectation);
        if (!result.meetsExpectation())
            failWithExpectationAndResultMessage(expectation, result);
        return result;
    }

    public <E, Q extends Quantity<E>> 
    ExpectationCheckResult<E, Q> resultOfChecking(FindingExpectation<E, Q, C> expectation) {
        return expectation.checkFrom(context);
    }
    
    public <E, Q extends Quantity<E>> 
    ExpectationCheckResult<E, Q> resultOfCheckingFailFast(FindingExpectation<E, Q, C> expectation) {
        return expectation.checkFromFailFast(context);
    }

    public <E, Q extends Quantity<E>> 
    Q thatWhichIsFoundBy(FindingExpectation<E, Q, C> expectation) {
        ExpectationCheckResult<E, Q> result = assertedResultOfChecking(expectation);
        return result.foundQuantity();
    }

    public <E, Q extends Quantity<E>> 
    boolean isMet(FindingExpectation<E, Q, C> expectation) {
        return resultOfChecking(expectation).meetsExpectation();
    }

    public <E, Q extends Quantity<E>> 
    void assertSatisfied(FindingExpectation<E, Q, C> expectation) {
        assertedResultOfChecking(expectation);
    }

    protected <E, Q extends Quantity<E>> void failWithExpectationAndResultMessage(
            FindingExpectation<E, Q, C> expectation, ExpectationCheckResult<E, Q> result) {
        failWith(newDescription()
                .appendText("Expected: ")
                .appendDescriptionOf(expectation)
                .appendText("\nbut:\n")
                .appendDescriptionOf(result));
    }
    
    public static <C> ExpectationChecker<C> checkerFor(C context){
        return new ExpectationChecker<C>(context);
    }
}
