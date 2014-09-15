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
package jhc.redsniff.internal.predicates;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class SomeItemsForPurpose<E> extends SomeItem<E> {

    private final String purpose;

    protected SomeItemsForPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public void describePredicateOnFinder(Finder<E, CollectionOf<E>, ?> finder, Description description) {
        super.describePredicateOnFinder(finder, description);
        description
                .appendText(" to " + purpose);

    }

    @Factory
    public static <E> Predicate<E, CollectionOf<E>> elementTo(String purpose) {
        return new SomeItemsForPurpose<E>(purpose);
    }
}
