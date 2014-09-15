package jhc.redsniff.internal.finders;

import jhc.redsniff.core.Locator;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.locators.FromElementListLocator;

//Currently Unused
public class FromElementListFinder<E> extends LocatorFinder<E, CollectionOf<E>>{
	public FromElementListFinder() {
		super(FromElementListFinder.<E>elementListLocator());
	}
	
	public static <E> Locator<E, CollectionOf<E>> elementListLocator() {
		return new FromElementListLocator<E>();
	}
}
