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
package jhc.redsniff.internal.core;

import java.util.Collection;

import jhc.redsniff.action.ActionDriver;
import jhc.redsniff.action.Controller;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.expectations.AlwaysFindsMultipleElementsChecker;
import jhc.redsniff.internal.finders.within.SFinderWithinMFinder;

/**
 * Specific type of {@link RedsniffTester} whose {@link #find(Finder)} method always returns a collection of elements, even when passed an {@link SFinder}
 * used by {@link #inEach(jhc.redsniff.core.MFinder)}  so that e.g. <tt>t.inEach(row() ).find(only(checkbox())</tt> will return multiple checkboxes - one for each 
 * of the multiple rows
 *
 * @param <E>
 * @param <C>
 */
public class AlwaysFindsMultipeElementsTester<E, C> extends RedsniffTester<E, C> {

	private final AlwaysFindsMultipleElementsChecker<C> checker;

	public AlwaysFindsMultipeElementsTester(AlwaysFindsMultipleElementsChecker<C> checker, Controller<E> controller, ActionDriver<E, C> actionDriver) {
		super(checker, controller, actionDriver);
		this.checker = checker;
	}

	@Override
	public <I, T, Q extends TypedQuantity<I, T>> Collection<I> find(
			Finder<I, Q, C> finder) {
		return checker.find(finder);
	}
}
