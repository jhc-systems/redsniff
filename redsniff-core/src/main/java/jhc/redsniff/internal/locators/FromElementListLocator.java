package jhc.redsniff.internal.locators;

import jhc.redsniff.core.Locator;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public class FromElementListLocator<E> implements Locator<E, CollectionOf<E>> {
	@Override
	public CollectionOf<E> findElementsFrom(CollectionOf<E> context) {
		return context;
	}

	@Override
	public String nameOfAttributeUsed() {
		return "ELEMENT_LIST";
	}

	@Override
	public void describeLocatorTo(Description description) {
		description.appendText("element list");
	}
}