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
package jhc.redsniff.core;

import static jhc.redsniff.core.FindingPredicates.*;
import static jhc.redsniff.util.Util.asMFinder;
import static jhc.redsniff.util.Util.asSFinder;
import jhc.redsniff.action.ActionPerformer;
import jhc.redsniff.action.ActionableFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.BaseFindingExpectation;
import jhc.redsniff.internal.expectations.ContextPrintingExpectation;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.expectations.MFinderExpectation;
import jhc.redsniff.internal.expectations.SFinderExpectation;
import jhc.redsniff.internal.predicates.Predicate;
import jhc.redsniff.internal.predicates.SingleItemMatching;
import jhc.redsniff.internal.predicates.SomeNumberOfItems;

import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

public final class FindingExpectations {

    private FindingExpectations() {
    }

    public static <E, C, Q extends Quantity<E>>
            FindingExpectation<E, Q, C> expectationOfSome(Finder<E, Q, C> finder) {
        return presenceOf(finder);
    }

    @SuppressWarnings("unchecked")
    public static <E, Q extends Quantity<E>, C>
            FindingExpectation<E, Q, C> presenceOf(Finder<E, Q, C> finder) {
        return expectationOf(presence(finder.getClass()), finder);
    }

    public static <E, C> FindingExpectation<E, Item<E>, C> expectationOfMatching(SFinder<E, C> finder, Matcher<? super E> matcher) {
        return expectationOf(SingleItemMatching.<E> singleItemMatches(matcher), finder);
    }

    public static <E, C>
            FindingExpectation<E, CollectionOf<E>, C> presenceOf(Matcher<Integer> cardinalityConstraint,
                    MFinder<E, C> finder) {
        return new MFinderExpectation<E, C>(SomeNumberOfItems.<E> aNumberOfElements(cardinalityConstraint), finder);
    }

    @SuppressWarnings("unchecked")
    public static <E, Q extends Quantity<E>, C>
            FindingExpectation<E, Q, C> absenceOf(Finder<E, Q, C> finder) {
        return expectationOf(absence(finder.getClass()), finder);
    }

    public static <E, C, Q extends Quantity<E>>
            FindingExpectation<E, Q, C> actionExpectation(ActionPerformer<E> performer, ActionableFinder<E, Q, C> finder) {
        return expectationOf(actionPredicate(performer, finder), finder);
    }

    public static <E, Q extends Quantity<E>, C> BaseFindingExpectation<E, Q, C> expectationOf(Predicate<E, Q> predicate,
            Finder<E, Q, C> finder) {
        if (finder instanceof MFinder)
            return newMFinderExpectation(predicate, finder);
        else
            return newSFinderExpectation(predicate, finder);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <Q extends Quantity<E>, C, E> BaseFindingExpectation<E, Q, C> newSFinderExpectation(
            Predicate<E, Q> predicate, Finder<E, Q, C> finder) {
        return (BaseFindingExpectation<E, Q, C>) (BaseFindingExpectation) new SFinderExpectation<E, C>(
                (Predicate<E, Item<E>>) predicate, asSFinder(finder));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <Q extends Quantity<E>, E, C> BaseFindingExpectation<E, Q, C> newMFinderExpectation(
            Predicate<E, Q> predicate, Finder<E, Q, C> finder) {
        return (BaseFindingExpectation<E, Q, C>) (BaseFindingExpectation) new MFinderExpectation<E, C>(
                (Predicate<E, CollectionOf<E>>) predicate, asMFinder(finder));
    }

    public static <E, C, Q extends Quantity<E>>
            FindingExpectation<E, Q, C> expectationWithPurpose(Finder<E, Q, C> finder, String purpose) {
        return expectationOf(forPurposePredicate(purpose, finder), finder);
    }

    public static <E, C, Q extends Quantity<E>> FindingExpectation<E, Q, C> contextPrinting(
            SelfDescribing contextDescription, FindingExpectation<E, Q, C> wrappedExpectation) {
        return new ContextPrintingExpectation<E, Q, C>(contextDescription, wrappedExpectation);
    }

}
