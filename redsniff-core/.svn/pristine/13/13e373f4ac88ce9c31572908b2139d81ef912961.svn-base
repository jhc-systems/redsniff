package jhc.redsniff.internal.finders.within;

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.finders.BaseSFinder;

import org.hamcrest.Description;

public class SFinderWithinSFinder<E0, C0, E1 extends C0, C1> extends BaseSFinder<E0, C1> {

    private final SFinder<E0, C0> innerFinder;
    private final SFinder<E1, C1> scopeFinder;

    public SFinderWithinSFinder(SFinder<E0, C0> innerFinder,
            SFinder<E1, C1> scopeFinder) {
        this.innerFinder = innerFinder;
        this.scopeFinder = scopeFinder;

    }

    public Item<E0> findFrom(C1 outerContext, Description notFoundDescription) {

        Description descriptionToDescribeFindingContextTo;

        if (scopeFinder instanceof SFinderWithinSFinder)
            descriptionToDescribeFindingContextTo = notFoundDescription;
        else
            descriptionToDescribeFindingContextTo = Describer.newDescription();

        Item<E1> innerContext = scopeFinder.findFrom(outerContext, descriptionToDescribeFindingContextTo);
        if (!innerContext.hasAmount()) {

            if (descriptionToDescribeFindingContextTo != notFoundDescription) {
                describeNotFindingOuterContextTo(notFoundDescription);
                notFoundDescription.appendText("\nbecause\n");
                Describer.concat(notFoundDescription, descriptionToDescribeFindingContextTo);
            }
            return Item.nothing();
        }
        ExpectationCheckResult<E0, Item<E0>> result = findWithinContext(innerContext);

        Item<E0> foundElement = foundElementFrom(result);
        if (!(foundElement.hasAmount())) {
            describeNotFindingInnerItemWithinOuterContext(result, innerContext.get(), notFoundDescription);
            return Item.nothing();
        }
        return foundElement;
    }

    private Item<E0> foundElementFrom(ExpectationCheckResult<E0, Item<E0>> result) {
        return result.foundQuantity();
    }

    private ExpectationCheckResult<E0, Item<E0>> findWithinContext(Item<E1> innerContext) {
        return checkerFor((C0)innerContext.get()).resultOfChecking(expectationOfSome(innerFinder));
    }

    private void describeNotFindingOuterContextTo(
            Description notFoundDescription) {
        notFoundDescription
                .appendDescriptionOf(scopeFinder)
                .appendText("\nwithin which to search for {")
                .appendDescriptionOf(innerFinder).appendText("}");

    }

    private void describeNotFindingInnerItemWithinOuterContext(
            ExpectationCheckResult<E0, Item<E0>> result, E1 outerElement,
            Description notFoundDescription) {

        notFoundDescription
                .appendText("\n within: ")
                .appendDescriptionOf(Describer.describable(outerElement))
                .appendText("\n\t");

        result.describeTo(notFoundDescription);
        notFoundDescription.appendText("\n");
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendDescriptionOf(innerFinder);

        if (scopeFinder instanceof SFinderWithinSFinder) {
            description.appendText(" within:");
            Describer.indent(description)
                    .appendText("the ")
                    .appendDescriptionOf(scopeFinder);
            Describer.outdent(description);
        }
        else
            description.appendText(", within a ").appendDescriptionOf(scopeFinder);
    }

    public static <I0, S0, I1 extends S0, S1> SFinderWithinSFinder<I0, S0, I1, S1> finderWithin(
            SFinder<I0, S0> innerFinder, SFinder<I1, S1> scopeFinder) {
        return new SFinderWithinSFinder<I0, S0, I1, S1>(innerFinder, scopeFinder);
    }

    @Override
    public <E2 extends C1, C2> SFinder<E0, C2> within(SFinder<E2, C2> outerScopeFinder) {
        return finderWithin(this.innerFinder, this.scopeFinder.within(outerScopeFinder));
    }

    @Override
    public <OE extends C1, OC> SFinderWithinMFinder<E0, C1, OE, OC> withinEach(MFinder<OE, OC> finder) {
            return new SFinderWithinMFinder<E0, C1, OE, OC>(this, finder);//SFinderWithinEachMFinder.finderWithinEach(this, scopeFinder);
    }

	@Override
	public SFinder<E0, C1> asOptimizedFinder() {
		return new SFinderWithinSFinder<>(innerFinder.asOptimizedFinder(), scopeFinder.asOptimizedFinder());
	}

//    @Override
//    public <OE extends C1, OC> MFinder<E0, OC> withinEach(MFinder<OE, OC> scopeFinder) {
//        return SFinderWithinEachMFinder.finderWithinEach(this, scopeFinder);
//    }

}
