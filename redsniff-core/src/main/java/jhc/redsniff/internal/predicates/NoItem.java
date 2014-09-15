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

import static jhc.redsniff.core.Describer.describable;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Item;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class NoItem<E> implements Predicate<E, Item<E>> {

    @Override
    public boolean isSatisfiedBy(Item<E> foundElements) {
       return foundElements.get()==null;
    }

    @Override
    public void diagnoseNotSatisfyingTo(Description dissatisfactionDescription,
            Item<E> foundElement, Description notFoundDescription) {
        dissatisfactionDescription
        .appendText("did find:")
        .appendDescriptionOf(describable(foundElement));
    }

    @Override
    public void describePredicateOnFinder(Finder<E, Item<E>, ?> finder, Description description) {
        description
        .appendText("not to find any ") 
        .appendDescriptionOf(finder);
    }
    
    @Factory
    public static <E> Predicate<E, Item<E>> nothing(){
        return new NoItem<E>();
    }

}
