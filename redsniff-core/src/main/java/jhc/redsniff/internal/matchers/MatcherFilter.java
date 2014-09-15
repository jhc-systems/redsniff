package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;

import java.util.ArrayList;

import jhc.redsniff.core.Describer;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.matchers.MatcherUtil.PrefixTextProvider;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

public class MatcherFilter<E> implements SelfDescribing{
   
    private final Matcher<? super E> matcher;

    public MatcherFilter(Matcher<? super E> matcher) {
        this.matcher = matcher;
    }

    public CollectionOf<E> filterResults(CollectionOf<E> elements, Description notFoundDescription) {
        Description mismatchesDescription = Describer.newDescription();
        CollectionOf<E> filteredElements = doFilter(elements, mismatchesDescription);
        if (filteredElements.isEmpty())
            Describer.concat(notFoundDescription, mismatchesDescription);
        return filteredElements;
    }

    protected CollectionOf<E> doFilter(CollectionOf<E> elements, Description mismatchesDescription) {
        CollectionOf<E> filteredElements = new CollectionOf<E>(new ArrayList<E>());
        for (E element : elements) {
            
			if (diagnosticMatch(element, prefixProviderForMismatch(element), mismatchesDescription))
                filteredElements.add(element);
        }
        return filteredElements;
    }

    private PrefixTextProvider prefixProviderForMismatch(final E element) {
    	return new PrefixTextProvider() {
			@Override
			public String getText() {
				return "\n" + prefixTextForMismatch(element);
			}
		};
	}

	protected boolean diagnosticMatch(E element, PrefixTextProvider prefixProvider, Description mismatchesDescription) {
        try {
            return matchAndDiagnose(matcher, element, mismatchesDescription, prefixProvider);
        } catch (Exception e) {
            describeExceptionAsMismatchButDontBlowUp(prefixProvider, mismatchesDescription, e);
            return false;
        }
    }

    protected void describeExceptionAsMismatchButDontBlowUp(PrefixTextProvider prefixProvider,
            Description mismatchesDescription, Exception e) {
        mismatchesDescription
               // .appendText(prefixProvider.getText())//this does actually make it blow up..
                .appendText(" could not check whether {")
                .appendDescriptionOf(matcher)
                .appendText("} because exception was thrown: "
                        + e.getMessage());
    }

    protected String prefixTextForMismatch(E element) {
        return newDescription()
                .appendDescriptionOf(Describer.describable(element))
                .appendText(" - not matched because ")
                .toString();
    }

    @Override
	public void describeTo(Description description) {
		if (matcher != null) //needed?
					matcher.describeTo(description);
	}
}
