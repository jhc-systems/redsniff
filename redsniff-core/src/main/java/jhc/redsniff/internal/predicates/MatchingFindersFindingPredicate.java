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

import java.util.List;

import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
//TODO
public class MatchingFindersFindingPredicate<E, C> implements Predicate<E, Quantity<E>>{
    
    public MatchingFindersFindingPredicate(List<MFinder<E, C>> findings){
        
    }
    
    @Override
    public boolean isSatisfiedBy(Quantity<E> foundElements) {
        return false;
    }

    @Override
    public void diagnoseNotSatisfyingTo(Description dissatisfactionDescription, Quantity<E> foundElements,
            Description notFoundDescription) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void describePredicateOnFinder(Finder<E, Quantity<E>, ?> finder, Description description) {
        // TODO Auto-generated method stub
        
    }

}
