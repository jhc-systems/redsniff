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

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Transformer;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;

import org.hamcrest.Description;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

//confusing extra concept? should just do as the AttributeFinder does and consider it to be "within"
public class TransformingMFinder<E, T, C> extends BaseMFinder<T, C> {
	
	private MFinder<E, C> elementFinder;
	private Transformer<E, T> transformer;

	public TransformingMFinder(MFinder<E, C> elementFinder, Transformer<E, T> transformer) {
    	this.elementFinder = elementFinder;
    	this.transformer = transformer;
    }

	@Override
	public void describeTo(Description description) {
		  description
		  .appendDescriptionOf(transformer)
		  .appendText(" formed from ")
          .appendDescriptionOf(elementFinder);
	}

    private CollectionOf<T> transform( Transformer<E,T> transformer, CollectionOf<E> initial, Description couldNotTransformDescription){
        return CollectionOf.collectionOf(Collections2.transform(initial.get(), function(transformer, couldNotTransformDescription)));
    }
    
    private Function<E, T> function(final Transformer<E,T> transformer, final Description couldNotTransformDescription) {
        return new Function<E, T>() {
            @Override
            public T apply(E element) {
                return transformer.transform(element, couldNotTransformDescription);
            }
        };
    }

	private CollectionOf<T> findTransformed(C context, Description notFoundDescription) {
	    ExpectationCheckResult<E, CollectionOf<E>> resultOfChecking = checkerFor(context).resultOfChecking(expectationOfSome(elementFinder));
	    if(!resultOfChecking.meetsExpectation())
	        resultOfChecking.describeTo(notFoundDescription);
	    
		return transform(transformer,resultOfChecking.foundQuantity(), notFoundDescription); 
	}

	@Override
	public CollectionOf<T> findFrom(C context,
			Description notFoundDescription) {
		Description baseNotFoundDescription = Describer.newDescription();
		CollectionOf<T> found = findTransformed(context, baseNotFoundDescription);
		if(found.isEmpty()) {
			notFoundDescription.appendText("Could not find anything to transform to ").appendDescriptionOf(transformer).appendText(":\n").appendDescriptionOf(elementFinder).appendText(":\n");
			Describer.concat(notFoundDescription,baseNotFoundDescription);
			return found;
		}
		return found;
	}

	@Override
	public MFinder<T, C> asOptimizedFinder() {
		return new TransformingMFinder<>(elementFinder.asOptimizedFinder(), transformer);
	}
}
