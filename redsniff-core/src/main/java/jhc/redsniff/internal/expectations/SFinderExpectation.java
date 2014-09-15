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

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.predicates.Predicate;

public class SFinderExpectation<E, C> extends  BaseFindingExpectation<E, Item<E>, C> {

    public SFinderExpectation(Predicate<E, Item<E>> expectation, SFinder<E, C> finder) {
        super(expectation, finder);
    }
    
}
