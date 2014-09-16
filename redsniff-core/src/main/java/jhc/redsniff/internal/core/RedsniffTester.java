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

import static jhc.redsniff.core.FindingExpectations.presenceOf;
import static jhc.redsniff.internal.finders.OnlyFinder.only;
import jhc.redsniff.action.ActionDriver;
import jhc.redsniff.action.Controller;
import jhc.redsniff.action.WithinEachContextActionDriver;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.describe.Describaliser;
import jhc.redsniff.internal.expectations.ExpectationChecker;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.expectations.WithinEachContextChecker;
import jhc.redsniff.internal.expectations.WithinSingleContextChecker;

import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;


/**
 * Parent class that exposes methods for interacting with the browser
 *
 * @param <E> the type of element to find/interact with
 * @param <C> the type of context in which to search
 */
public abstract class RedsniffTester<E, C> {

    protected ActionDriver<E, C> actionDriver;
    protected ExpectationChecker<C> checker;
    protected Controller<E> controller;

    public RedsniffTester(ExpectationChecker<C> checker, Controller<E> controller, ActionDriver<E, C> actionDriver) {
        this.checker = checker;
        this.controller = controller;
        this.actionDriver = actionDriver;
        registerDescribalisers();
    }

    /**
     * Finds an element in a context using a {@link Finder}
     * @param finder
     * @return
     */
	public abstract <I, T, Q extends TypedQuantity<I, T>> Object find(Finder<I, Q, C> finder);

    // browser actions
	
	/**
	 * Finds the element described and performs a click on it using the configured {@link ActionDriver}.
	 * <p/>
	 * If given an {@link MFinder} like button(), 
	 * it will assume uniqueness and insert only(..) 
	 * <p/>
	 * If you actually mean to click on every button found, then you can use t.clickOn( each(button() )
	 * @param finder finder to find the element to interact with
	 */
    public <Q extends Quantity<E>> void clickOn(Finder<E, Q, C> finder) {
        actionDriver.clickOn(finder);
    }
    
    /**
	 * Find the element described and tabs out of it. 
	 * If given an {@link MFinder} like textbox(), 
	 * it will assume uniqueness and insert only(..) 
	 * <p/>
	 * If you actually mean to tab out of every textbox found, then you can use t.tab( each(button() )
	 * @param finder finder to find the element to interact with
	 */
    public <Q extends Quantity<E>> void tab(Finder<E, Q, C> finder) {
        actionDriver.tab(finder);
    }
    
    /**
	 * Finds the element described (such as a form) and submits it. 
	 *
	 * @param finder finder to find the element to interact with
	 */
    public <Q extends Quantity<E>> void submit(Finder<E, Q, C> finder) {
        actionDriver.submit(finder);
    }
    
    /**
	 * Type the supplied text into the item found by finder, overwriting or appending to existing text.
	 * <p/>
	 * Usual rules on uniqueness and each apply.
	 * <p/>
	 * Can use syntactic sugar method into(...) which just takes and returns a finder, simply to read better
	 * @param finder finder to find the element to interact with
	 * @param append true to append to any existing text in the box, false to overwrite it
	 */
    public <Q extends Quantity<E>> void type(String input,
            Finder<E, Q, C> finder, boolean append) {
        actionDriver.type(input, finder, append);
    }

    /**
	 * Type the supplied text into the item found by finder, overwriting any existing text. 
	 * <p/>
	 * Usual rules on uniqueness and each apply.
	 * <p/>
	 * Can use syntactic sugar method into(...) which just takes and returns a finder, simply to read better
	 * @param finder finder to find the element to interact with
	 */
    public <Q extends Quantity<E>> void type(String input,
            Finder<E, Q, C> finder) {
        actionDriver.type(input, finder);
    }

    /**
	 * Clear text on the item found by finder. 
	 * <p/>
	 * Usual rules on uniqueness and each apply.
	 * <p/>
	 * @param finder finder to find the element to interact with
	 */
    public <Q extends Quantity<E>> void clear(Finder<E, Q, C> finder) {
        actionDriver.clear(finder);
    }

    /**
     * @see #clickOn(Finder)
     * @param finder
     */
    public <Q extends Quantity<E>> void tick(Finder<E, Q, C> finder) {
        actionDriver.tick(finder);
    }

    /**
     * @see #clickOn(Finder)
     * @param finder
     */
    public <Q extends Quantity<E>> void choose(Finder<E, Q, C> finder) {
        actionDriver.choose(finder);
    }
    
    /**
     * Navigate to supplied url
     * @param finder
     */
    public void goTo(String url) {
        controller.goTo(url);
    }
    
    /**
     * Close the browser
     */
    public void quit(){
        controller.quit();
    }

    // assertions

    /**
     * Asserts the given {@link FindingExpectation} on the current context is satisfied, or throw an AssertionError with diagnostic message if not
     * @param expectation
     * <p/>
     * For example:
     * <pre>assertThe(presenceOf( div().that( hasName("search") )) </pre>
     */
    public <I, Q extends Quantity<I>> void assertThe(FindingExpectation<I, Q, C> expectation) {
        checker.assertThe(expectation);
    }

    /**
     * Uses the finder to find a unique element and then checks the given matcher matches the result
     * @param finder
     * @param matcher
     * <p/>
     * For example:
     * <pre>t.assertThatThe( textbox().that( hasName("search") ),  hasText("Some text"))</pre>
     * Will throw an exception with explanatory text if either:
     * * the finder cannot find anything, or finds multiple results
     * * the found element does not match the matcher
     */
    public <I, Q extends Quantity<I>> void assertThatThe(Finder<I, Q, C> finder,
            Matcher<I> matcher) {
        checker.assertThatThe(finder, matcher);
    }

    
    public <I, Q extends Quantity<I>> boolean meetsExpectation(FindingExpectation<I, Q, C> expectation) {
    	return checker.isMet(expectation);
    }
    
    /**
     * Returns true/false on whether a given finder finds any results
     * @param finder
     * @return
     */
    public <I, Q extends Quantity<I>> boolean isPresent(Finder<I, Q, C> finder) {
        return checker.isPresent(finder);
    }

    /**
     * Throw an AssertionError with diagnostic message if the supplied finder finds any results
     * @param finder
     * <p/>
     * For example:
     * assertAbsenceOf( div().that(hasName("nonexistent")))
     */
    public <I, Q extends Quantity<I>> void assertAbsenceOf(Finder<I, Q, C> finder) {
        checker.assertAbsenceOf(finder);
    }
    
    /**
     * Throw an AssertionError with diagnostic message if the supplied finder does not find any results
     * @param finder
     * <p/>
     * For example:
     * assertPresenceOf( div().that(hasName("search")))
     */
    public <I, Q extends Quantity<I>> void assertPresenceOf(Finder<I, Q, C> finder) {
        checker.assertPresenceOf(finder);
    }
    
    
    /**
     * Throw an AssertionError with diagnostic message if the number of results found by the 
     * supplied finder does not satisfy the supplied cardinality constraint
     * @param cardinalityConstraint - a matcher of integer stating how many results are expected
     * @param finder
     * <p/>
     * For example:
     * assertPresenceOf( atLeast(3), textbox().that(hasName("search")))
     * 
     * assertPresenceOf( exactly(2), textbox().that(hasName("search")))
     * 
     * {@see NumericalMatchers}
     */
    public <I> void assertPresenceOf(Matcher<Integer> cardinalityConstraint, MFinder<I, C> finder) {
        assertThe(presenceOf(cardinalityConstraint,finder)); 
    }

    /**
     * Use for building complex expressions where interaction is required from a specific subcontext.
     * @param innerContextFinder a finder for a context from which subsequent interactions will be made
     * @return a {@link RedsniffTester} rooted on results of first finder
     * <p/>
     * For example:
     * <pre>inEach(form())
     * 			.clickOn(first(button()))</pre>
     * will go through each form and click the first button found within each one.
     * <p/>
     * <pre>inEach(form())
     * 			.assertPresenceOf(exactly(2), button()))</pre>
     * 
     * Checks that each form contains exactly 2 buttons
     * <p/>
     * <pre>inEach(form())
     * 			.find(only(button()))</pre>
     * would return a <strong>Collection</strong> of button elements - the unique one found in each form
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <C1, E0 extends C1, E1> AlwaysFindsMultipeElementsTester inEach(MFinder<E0, C> innerContextFinder) {
        WithinEachContextChecker<C1, E0, C> inEachContextChecker = checker
                .<C1, E0> inEachContextFoundBy(innerContextFinder);
        //TODO - currently returns raw type to avoid error - would be nice to sort out
        return new AlwaysFindsMultipeElementsTester<E1, C1>(inEachContextChecker, (Controller<E1>) controller,
                new WithinEachContextActionDriver<E1, C1, E0,C>(inEachContextChecker, (Controller<E1>) controller) {
                });
    }
    
    /**
     * Use for building complex expressions where interaction is required from a specific subcontext.
     * @param innerContextFinder a finder for a context from which subsequent interactions will be made
     * @return a {@link RedsniffTester} rooted on results of first finder
     * <p/>
     * For example:
     * <pre>inThe(first(form()))
     * 			.clickOn(first(button()))</pre>
     * will find the first form and click the first button found therein.
     * <p/>
     * <pre>inThe(second(form()))
     * 			.assertPresenceOf(exactly(2), button()))</pre>
     * 
     * Checks that the second form contains exactly 2 buttons
     * <p/>
     * <pre>inThe(only(form()))
     * 			.find(only(button()))</pre>
     * would return a <strong>Collection</strong> of button elements - the unique one found in each form
     */
    //TODO - currently returns raw type to avoid error - would be nice to sort out
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <C1, E0 extends C1, E1> RedsniffQuantityFindingTester inThe(SFinder<E0, C> innerContextFinder) {
        WithinSingleContextChecker<C1, E0, C> inEachContextChecker = checker.inSingleContextFoundBy(innerContextFinder);
        	   

        ActionDriver<E1, C1> actionDriver = new ActionDriver<E1, C1>(inEachContextChecker, (Controller<E1>) controller);
        		
		return new RedsniffQuantityFindingTester<E1, C1>(inEachContextChecker, (Controller<E1>) controller,
                actionDriver);
    }
    
    @SuppressWarnings( "rawtypes" )
    public <C1, E0 extends C1, E1> RedsniffQuantityFindingTester inThe(MFinder<E0, C> innerContextFinder) {
    	return inThe(only(innerContextFinder));
    }
    
    
    /**
     * Subclasses should implement this to register {@link Describaliser}s - classes able to create {@link SelfDescribing} 
     * decorations of objects of particular types. 
     */
    protected  void registerDescribalisers(){
		  
	}
}