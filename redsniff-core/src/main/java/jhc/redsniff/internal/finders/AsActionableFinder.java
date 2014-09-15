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

import static jhc.redsniff.internal.finders.OnlyFinder.only;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import jhc.redsniff.action.ActionPerformer;
import jhc.redsniff.action.ActionableFinder;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.finders.within.SFinderWithinMFinder;
import jhc.redsniff.internal.finders.within.SFinderWithinSFinder;

public abstract class AsActionableFinder {

	private static final class TheOnlyActionableFinder<E, C> extends
			WrappingSFinder<E, C> implements ActionableFinder<E, Item<E>, C> {
		private TheOnlyActionableFinder(SFinder<E, C> wrappedFinder) {
			super(wrappedFinder);
		}

		@Override
		public <OE extends C, OC> SFinder<E, OC> within(
				SFinder<OE, OC> scopeFinder) {
			return SFinderWithinSFinder.finderWithin(this, scopeFinder);
		}

		@Override
		public <OE extends C, OC> SFinderWithinMFinder<E, C, OE, OC> withinEach(
				MFinder<OE, OC> scopeFinder) {
			return new SFinderWithinMFinder<E, C, OE, OC>(this, scopeFinder);
		}

		@Override
		public void performAction(ActionPerformer<E> performer, Item<E> item) {
			performer.performAction(item.get());
		}

		@Override
		public SFinder<E, C> asOptimizedFinder() {
			return new TheOnlyActionableFinder<>(wrappedFinder.asOptimizedFinder());
		}

		@Override
		public void describeTypeOfActionTo(Description description) {
			//nothing to add
		}
	}

	private static final class EachItemActionableMFinder<E, C> extends
			WrappingMFinder<E, C> implements
			ActionableFinder<E, CollectionOf<E>, C> {
		private EachItemActionableMFinder(MFinder<E, C> wrappedFinder) {
			super(wrappedFinder);
		}

		//TODO this 'each' behaviour should probably be part of a special type of ActionPerformer wrapper, rather than on the finder
		@Override
		public void performAction(ActionPerformer<E> performer,
				CollectionOf<E> items) {
			for (E item : items)
				performer.performAction(item);
		}

		@Override
		public MFinder<E, C> asOptimizedFinder() {
			return new EachItemActionableMFinder<E, C>(
					wrappedFinder.asOptimizedFinder());
		}

		@Override
		public MFinder<E, C> that(Matcher<? super E> elementMatcher) {
			return new EachItemActionableMFinder<>(
					wrappedFinder.that(elementMatcher));
		}

		@Override
		public MFinder<E, C> optimizedWith(Matcher<? super E> matcher) {
			return new EachItemActionableMFinder<>(
					wrappedFinder.optimizedWith(matcher));
		}

		@Override
		public void describeTypeOfActionTo(Description description) {
			description.appendText(" each one found");
		}
	}

	public static <E, C> ActionableFinder<E, CollectionOf<E>, C> each(
			MFinder<E, C> finder) {
		return new EachItemActionableMFinder<E, C>(finder);
	}

	public static <E, Q extends Quantity<E>, C> ActionableFinder<E, Item<E>, C> theOnly(
			Finder<E, Q, C> finder) {
		return new TheOnlyActionableFinder<E, C>(only(finder));
	}

}
