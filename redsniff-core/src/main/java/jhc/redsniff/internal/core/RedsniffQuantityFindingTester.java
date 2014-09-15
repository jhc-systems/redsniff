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


import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import static jhc.redsniff.internal.finders.OnlyFinder.only;
import jhc.redsniff.action.ActionDriver;
import jhc.redsniff.action.Controller;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.expectations.ExpectationChecker;

/**
 * Type of RedsniffTester whose {@link #find(Finder)} method returns a quantity of elements according to the type of the finder
 *
 * @param <E>
 * @param <C>
 */
public class RedsniffQuantityFindingTester<E, C> extends RedsniffTester<E, C> {

    public RedsniffQuantityFindingTester(C context, Controller<E> controller){
        this(controller, checkerFor(context));
    }
    
    public RedsniffQuantityFindingTester(Controller<E> controller, ExpectationChecker<C> checker){
        this(checker, controller, new ActionDriver<E, C>(checker, controller));
    }
    
	public RedsniffQuantityFindingTester(ExpectationChecker<C> checker,
			Controller<E> controller, ActionDriver<E, C> actionDriver) {
		super(checker, controller, actionDriver);
	}
	
	/**
	 * Finds an element of type I in a context of type C
	 * If passed an {@link MFinder}, such as <pre>div()</pre> it returns a collection of all such elements on the page. 
	 * <p/>
	 * If passed an {@link SFinder} such as <pre>only(div())</pre> then it returns that one element. 
	 * <p/>
	 * In either case, it will throw an AssertionError (with explanation) if the expected item(s) are not found.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <I, T, Q extends TypedQuantity<I, T>> T find(
			Finder<I, Q, C> finder) {
		return (T) checker.find(finder);
	}

	/**
	 * The unique item found by the finder.  
	 * Synonym for 
	 * <pre>find(only(finder))</pre>
	 * @param finder
	 * @return
	 */
	public <I, Q extends Quantity<I>> I findThe(Finder<I, Q, C> finder) {
		return find(only(finder));
	}
	
}
