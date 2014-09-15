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
package jhc.redsniff.internal.finders;

import static jhc.redsniff.core.FindingExpectations.expectationOf;
import static jhc.redsniff.internal.core.Item.oneOf;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import static jhc.redsniff.util.Util.asMFinder;
import static jhc.redsniff.util.Util.asSFinder;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.predicates.UniqueItem;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class OnlyFinder<E, C> extends BaseSFinder<E, C>{

	private final MFinder<E, C> innerFinder;
	
	public OnlyFinder(MFinder<E, C> innerFinder) {
		this.innerFinder = innerFinder;
	}

    @Override
    public Item<E> findFrom(C context,
            Description notFoundDescription) {
        ExpectationCheckResult<E, CollectionOf<E>> result = checkerFor(context).
                resultOfChecking(expectationOf(UniqueItem.<E> uniqueElement(), innerFinder));
        if (result.meetsExpectation())
			return result.foundQuantity().first();
		else
			return diagnosedEmpty(notFoundDescription, result);
    }

	private Item<E> diagnosedEmpty(Description notFoundDescription,
			ExpectationCheckResult<E, CollectionOf<E>> result) {
		if (result.foundQuantity().isEmpty())
			return noneFound(notFoundDescription, result);
		else
			return wrongNumberFound(notFoundDescription, result);
	}

	private Item<E> wrongNumberFound(Description notFoundDescription,
			ExpectationCheckResult<E, CollectionOf<E>> result) {
		notFoundDescription
			.appendText("Was not unique: ")
			.appendDescriptionOf(innerFinder)
			.appendText("\n");
		result.describeTo(notFoundDescription);
		return oneOf(null);
	}

	private Item<E> noneFound(Description notFoundDescription,
			ExpectationCheckResult<E, CollectionOf<E>> result) {
		notFoundDescription
				.appendText("Did not find any ")
		        .appendDescriptionOf(innerFinder)
		        .appendText("\n");
		result.describeTo(notFoundDescription);
		return oneOf(null);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("unique ").appendDescriptionOf(innerFinder);
	}

    @Factory
    public static <E, Q extends Quantity<E>, C> SFinder<E, C> only(Finder<E, Q, C> finder){
        if (finder instanceof MFinder) 
               return new OnlyFinder<E, C>(asMFinder(finder));
        else 
                return asSFinder(finder);
    }
    
    @Factory
    public static <E, Q extends Quantity<E>, C> SFinder<E, C> the(Finder<E, Q, C> finder){
        return only(finder);
    }

	@Override
	public SFinder<E, C> asOptimizedFinder() {
		return new OnlyFinder<>(innerFinder.asOptimizedFinder());
	}
}
