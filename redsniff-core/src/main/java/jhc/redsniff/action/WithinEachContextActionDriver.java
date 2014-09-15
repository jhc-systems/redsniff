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
