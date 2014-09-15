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

import jhc.redsniff.core.Describer;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.predicates.Predicate;

import org.hamcrest.Description;

public abstract class BaseFindingExpectation<E, Q extends Quantity<E>, C> implements FindingExpectation<E, Q, C> {

    private final Finder<E, Q, C> finder;
    private final Predicate<E, Q> predicate;

    protected BaseFindingExpectation(Predicate<E, Q> predicate, Finder<E, Q, C> finder) {
        this.finder = finder;
        this.predicate = predicate;
    }

    protected boolean expectationMetBy(Q foundElements) {
        return  predicate.isSatisfiedBy(foundElements);
    }

    @Override
    public void describeTo(Description description) {
            predicate.describePredicateOnFinder(finder, description);
    }

    @Override
	public ExpectationCheckResult<E, Q> checkFrom(C context) {
		Description optimtizedNotFoundDescription = Describer.newDescription();
		Q foundElements = checkUsingOptimization(context, optimtizedNotFoundDescription);
		if (!expectationMetBy(foundElements)) {
			// here, if we unexpectedly get no elements, we should try to repeat
			// the find without swapping so can get a diagnostic message			
			try {
			return checkUnoptimized(context,  Describer.newDescription());
			}catch(UnoptimizedNotPossibleException e){
				return unsatisfactoryResult(foundElements, optimtizedNotFoundDescription);
			}
		}
		else 
			return satisfactoryResult(foundElements);
	}

    @Override
    public ExpectationCheckResult<E, Q> checkFromFailFast(C context) {
    	Description notFoundDescription = Describer.newDescription();
    	Q foundElements = checkUsingOptimization(context, notFoundDescription);
    	return resultFor(notFoundDescription, foundElements);
    }

	private Q checkUsingOptimization(C context, Description notFoundDescription) {
		Q foundElements = finder
				.asOptimizedFinder().findFrom(context, notFoundDescription);
		return foundElements;
	}
    

	private ExpectationCheckResult<E, Q> resultFor(
			Description notFoundDescription, Q foundElements) {
		if (!expectationMetBy(foundElements)) {
			return unsatisfactoryResult(foundElements, notFoundDescription);
		}
		else 
			return satisfactoryResult(foundElements);
	}

	public ExpectationCheckResult<E, Q> checkUnoptimized(C context,
			Description notFoundDescription) {
		Q foundElements = finder.findFrom(context, notFoundDescription);
		return resultFor(notFoundDescription, foundElements);
	}

	protected ExpectationCheckResult<E, Q> unsatisfactoryResult(Q foundElements, Description notFoundDescription) {
        Description dissatisfactionDescription = Describer.newDescription();
        predicate.diagnoseNotSatisfyingTo(dissatisfactionDescription, foundElements, notFoundDescription);
        return ExpectationCheckResult.unsatisfactoryResult(foundElements, dissatisfactionDescription);
    }

    protected ExpectationCheckResult<E, Q> satisfactoryResult(Q foundElements) {
        return ExpectationCheckResult.satisfactoryResult(foundElements);
    }

    @Override
    public String toString() {
        return Describer.newDescription().appendDescriptionOf(this).toString();
    }

}