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
package jhc.redsniff.internal.finders.within;

import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.finders.BaseMFinder;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Composite {@link MFinder} class representing a {@link MFinder} within an {@link SFinder}
 * 
 * @see {@link MFinder#withinThe(SFinder)
 *
 * @param <E0> the type of element found by the inner finder and which this composite finder will return
 * @param <C0> the type of context the inner finder can search in
 * @param <E1> the type of element found by the outer finder. must be a subtype of C0
 * @param <C1> the type of context searched by the outer finder, and which this composite finder will search in
 */
public class MFinderWithinSFinder<E0, C0, E1 extends C0, C1> extends BaseMFinder<E0, C1> {

    private final MFinder<E0, C0> innerFinder;
    private final SFinder<E1, C1> scopeFinder;

    public MFinderWithinSFinder(MFinder<E0, C0> innerFinder, SFinder<E1, C1> scopeFinder) {
        this.innerFinder = innerFinder;
        this.scopeFinder = scopeFinder;
    }

    @Override
    public CollectionOf<E0> findFrom(C1 outerContext, Description notFoundDescription) {
        Description descriptionToDescribeFindingContextTo;

        if (scopeFinder instanceof MFinderWithinSFinder)
            descriptionToDescribeFindingContextTo = notFoundDescription;
        else
            descriptionToDescribeFindingContextTo = Describer.newDescription();

        Item<E1> contextItem = scopeFinder.findFrom(outerContext, descriptionToDescribeFindingContextTo);
        if (!contextItem.hasAmount()) {

            if (descriptionToDescribeFindingContextTo != notFoundDescription) {
                describeNotFindingOuterContextTo(notFoundDescription);
                notFoundDescription.appendText("\nbecause\n");
                Describer.concat(notFoundDescription, descriptionToDescribeFindingContextTo);
            }
            return CollectionOf.empty();
        }

        C0 context = contextItem.get(); 

        Description innerNotFoundDescription = Describer.newDescription();
        CollectionOf<E0> foundElements =innerFinder.findFrom(context, innerNotFoundDescription);

        if (foundElements.isEmpty()) {
            describeNotFindingInnerItemWithinContext(innerNotFoundDescription, context, notFoundDescription);
            return CollectionOf.empty();
        }
        return foundElements;

    }

    private void describeNotFindingInnerItemWithinContext(
            Description innerNotFoundDescription,
            C0 context,
            Description notFoundDescription) {
        notFoundDescription
                .appendText("\n within: ")
                .appendDescriptionOf(Describer.describable(context))
                .appendText("\n\t");
        Describer.concat(notFoundDescription, innerNotFoundDescription);
        notFoundDescription.appendText("\n");
    }

    private void describeNotFindingOuterContextTo(
            Description notFoundDescription) {
        notFoundDescription
                .appendDescriptionOf(scopeFinder)
                .appendText("\nwithin which to search for {")
                .appendDescriptionOf(innerFinder).appendText("}");

    }

    @Override
    public void describeTo(Description description) {
        description
                .appendDescriptionOf(innerFinder);

        if (scopeFinder instanceof MFinderWithinMFinder) {
            description.appendText(" within:");
            Describer.indent(description)
                    .appendText("the ")
                    .appendDescriptionOf(scopeFinder);
            Describer.outdent(description);
        }
        else
            description.appendText(", within the ").appendDescriptionOf(scopeFinder);

    }

    //*that* is always applied before *within*...
    @Override
    public MFinder<E0, C1> that(Matcher<? super E0> elementMatcher) {
        return MFinderWithinSFinder.finderWithin(innerFinder.that(elementMatcher), scopeFinder);
    }

    @Override
    public <OE extends C1, OC> MFinder<E0, OC> withinA(MFinder<OE, OC> outerScopeFinder) {
        return MFinderWithinMFinder.finderWithin(this.innerFinder, this.scopeFinder.withinEach(outerScopeFinder));
    }

   
    @Override
    public <OE extends C1, OC> MFinder<E0, OC> withinThe(SFinder<OE, OC> outerScopeFinder) {
      return MFinderWithinSFinder.finderWithin(this.innerFinder, this.scopeFinder.within(outerScopeFinder));
    } 
    
    public static <I0, S0, I1 extends S0, S1> MFinderWithinSFinder<I0, S0, I1, S1> finderWithin(
            MFinder<I0,S0> innerFinder, SFinder<I1,S1> scopeFinder) {
        return new MFinderWithinSFinder<I0, S0, I1, S1>(innerFinder, scopeFinder);
    }

	@Override
	public MFinder<E0, C1> asOptimizedFinder() {
		return MFinderWithinSFinder.finderWithin(innerFinder.asOptimizedFinder(), scopeFinder.asOptimizedFinder());
	}

	@Override
	public MFinder<E0, C1> optimizedWith(Matcher<? super E0> matcher) {
		return MFinderWithinSFinder.finderWithin(innerFinder.optimizedWith(matcher), scopeFinder);
	}
	
}
