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

import static jhc.redsniff.matchers.numerical.NumericalMatchers.*;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

public class UniqueItem<E> extends SomeNumberOfItems<E> {
    private static final class SingleElementMatcher extends
            TypeSafeMatcher<Integer> implements CustomNumberMatcher {
        @Override
        public void describeTo(Description description) {
            description.appendText("a (unique)");
        }

        @Override
        protected boolean matchesSafely(Integer item) {
            return exactly(1).matches(item);
        }
    }

    protected UniqueItem() {
        super(new SingleElementMatcher());
    }

    @Factory
    public static <E> Predicate<E, CollectionOf<E>> uniqueElement() {
        return new UniqueItem<E>();
    }
}
