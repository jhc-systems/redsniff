package jhc.redsniff.internal.finders;

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Transformer;

import org.hamcrest.Description;

public class TransformingContextSFinder<T,C> extends BaseSFinder<T, C>{

    private final Transformer<C,T> transformer;

    public TransformingContextSFinder(Transformer<C,T> transformer) {
        this.transformer = transformer;
    }

    @Override
    public Item<T> findFrom(C element, Description notFoundDescription) {
        return Item.oneOf(transformer.transform(element, notFoundDescription));
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(transformer);
    }

	@Override
	public SFinder<T, C> asOptimizedFinder() {
		return this;
	}

}
