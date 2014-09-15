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
package jhc.redsniff.action;

import static jhc.redsniff.core.Describer.asString;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.expectations.WithinEachContextChecker;

//C1 is inner context
//E1 is inner element that controller can operate on
//C0 is outer context -what we need parentContext to be and what we need the context finder to find from
//E0 is what the context finder finds - it must extend C1
public abstract class WithinEachContextActionDriver<E1,C1, E0 extends C1, C0> extends ActionDriver<E1, C1>   {

	private final WithinEachContextChecker<C1, E0, C0> withinEachContextChecker;

	public WithinEachContextActionDriver(WithinEachContextChecker<C1, E0, C0> withinEachContextChecker, Controller<E1> controller) {
		super(withinEachContextChecker, controller);
		this.withinEachContextChecker = withinEachContextChecker;
	}

	@Override
	public <Q extends Quantity<E1>> void performActionOn(ActionableFinder<E1, Q, C1> finder, FindingExpectation<E1, Q, C1> actionExpectation,
			ActionPerformer<E1> performer) {
		FindingExpectation<E0, CollectionOf<E0>, C0> expectingToSearchWithinTo = withinEachContextChecker.expectingToSearchWithinTo(asString(actionExpectation));
		for(C1 context:withinEachContextChecker.contextsToSearch(expectingToSearchWithinTo))
			new ActionDriver<E1, C1>(checkerFor(context), controller).performActionOn(finder,actionExpectation,performer);
	}
}
