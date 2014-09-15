package jhc.redsniff.action;

import static jhc.redsniff.core.FindingExpectations.actionExpectation;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.ExpectationChecker;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.finders.AsActionableFinder;

import org.hamcrest.Description;

/**
 * Exposes methods for performing actions on elements, such as clicking, 
 * using a checker to get the elements and a controller to perform the actions 
 *
 * @param <E> the type of element to interact with
 * @param <C> the type of context in which the elements may be found
 */
public class ActionDriver<E, C>    {

	protected ExpectationChecker<C> checker;
	protected Controller<E> controller;

	/**
	 * @param checker - for checking the expectation for the elements we require to interact with
	 * @param controller - provides the ability to perform the actions
	 */
	public ActionDriver(ExpectationChecker<C> checker, Controller<E> controller){
	    this.checker = checker;
		this.controller = controller;
	}

    public <Q extends Quantity<E>> void type(String input, Finder<E, Q, C> finder) {
        type(input,finder,false);
    }

    /**
     * alternate version with optional append flag
     * @param input
     * @param finder
     * @param append
     */
    public <Q extends Quantity<E>> void type(String input, Finder<E, Q, C> finder,boolean append) {
        performActionOn(actionable(finder), typing(input, append));
    }

    /**
     * Clear a text field
     */
    public <Q extends Quantity<E>> void clear(Finder<E, Q, C> finder) {
        performActionOn(actionable(finder),  clearing());
    }

    /**
     * tick a checkbox (same as clicking)
     */
    public <L,Q extends Quantity<E>> void tick(Finder<E, Q, C> finder) {
        performActionOn(actionable(finder), ticking());
    }
    
    /**
     * choose on a drop-down (same as clicking)
     */
    public <L,Q extends Quantity<E>> void choose(Finder<E, Q, C> finder) {
        performActionOn(actionable(finder),  choosing());
    }
    
    /**
     * click on an element
     * @param finder
     */
	public <L,Q extends Quantity<E>> void clickOn(Finder<E, Q, C> finder) {
		performActionOn(actionable(finder),  clicking());
	}
	
	/**
	 * Submit a form
	 * @param finder
	 */
	public <L,Q extends Quantity<E>> void submit(Finder<E, Q, C> finder) {
		performActionOn(actionable(finder),  submitting());
	}
	
	/**
	 * Tab out of a text field (
	 */
	public <L,Q extends Quantity<E>> void tab(Finder<E, Q, C> finder) {
	    performActionOn(actionable(finder), tabbing());
	}
	

	@SuppressWarnings("unchecked")
	public <Q extends Quantity<E>> void performActionOn(ActionableFinder<E, Q, C> finder, FindingExpectation<E, Q, C> actionExpectation, ActionPerformer<E> performer) {
	    Q someOfElement = (Q) checker.quantityFoundBy(actionExpectation);
		finder.performAction(performer, someOfElement);
	}
    
    private <Q extends Quantity<E>> void performActionOn(ActionableFinder<E, Q, C> finder, ActionPerformer<E> performer) {
    	performActionOn(finder, actionExpectation(performer, finder), performer);
    }
	
	@SuppressWarnings("unchecked")
    private static <E, Q extends Quantity<E>, C> ActionableFinder<E, Q, C> actionable(Finder<E, Q, C> finder){
	    if(finder instanceof ActionableFinder)
	        return (ActionableFinder<E, Q, C>) finder;
	    else
	        return (ActionableFinder<E, Q, C>) AsActionableFinder.theOnly(finder);
	}
	
 	private ActionPerformer<E> typing(final String input, final boolean append) {
        return new ActionPerformer<E>() {
            @Override
            public void performAction(E item) {
                if(!append)
                    controller.clear(item);
                controller.type(input, item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("type '"+input+"' into");
            }
            
        };
    }
 	
    private ActionPerformer<E> clearing() {
        return new ActionPerformer<E>(){
            @Override
            public void performAction(E item) {
                controller.clear(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("clear");
                
            }
            };
    }
    
    private ActionPerformer<E> clicking() {
        return new ActionPerformer<E>(){
            @Override
            public void performAction(E item) {
                controller.click(item);
            }
            public void describeTo(Description description){
                description.appendText("click on");
            }
        };
    }
    
    private ActionPerformer<E> tabbing() {
        return new ActionPerformer<E>(){
            @Override
            public void performAction(E item) {
                controller.tab(item);
            }
            public void describeTo(Description description){
                description.appendText("tab");
            }
        };
    }
    
    
    private ActionPerformer<E> submitting() {
        return new ActionPerformer<E>(){
            @Override
            public void performAction(E item) {
                controller.submit(item);
            }
            public void describeTo(Description description){
                description.appendText("submit");
            }
        };
    }
    
    private ActionPerformer<E> ticking() {
        return new ActionPerformer<E>(){
            @Override
            public void performAction(E item) {
                controller.click(item);
            }
            public void describeTo(Description description){
                description.appendText("tick");
            }
        };
    }
    
    private ActionPerformer<E> choosing() {
        return new ActionPerformer<E>(){
            @Override
            public void performAction(E item) {
            	controller.click(item);
            }
            public void describeTo(Description description){
                description.appendText("choose");
            }
        };
    }
}
