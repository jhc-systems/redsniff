package jhc.redsniff.webdriver.table;

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.core.CollectionOf.collectionOf;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;

import java.util.ArrayList;

import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.TableItem;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.LocatorFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.openqa.selenium.WebElement;

public class ElementOfTableItemFinder<T extends TableItem, C> extends
		LocatorFinder<WebElement, C> {

	public ElementOfTableItemFinder(MFinder<T, C> tableItemFinder) {
		super(createLocator(tableItemFinder));
	}

	private static <T extends TableItem, C> Locator<WebElement, C> createLocator(
			final MFinder<T, C> tableItemFinder) {
		return new Locator<WebElement, C>() {
			@Override
			public CollectionOf<WebElement> findElementsFrom(C context) {
				CollectionOf<WebElement> elements = collectionOf(new ArrayList<WebElement>());
				for (TableItem tableItem : checkerFor(context).thatWhichIsFoundBy(expectationOfSome(tableItemFinder))) {
					elements.add(tableItem.element());
				}
				return elements;
			}

			@Override
			public String nameOfAttributeUsed() {
				return "TABLEITEM_ELEMENT";
			}

			@Override
			public void describeLocatorTo(Description description) {
				description.appendText("element of {").appendDescriptionOf(
						tableItemFinder);
			}
		};
	}

	@Factory
	public static <T extends TableItem, C> MFinder<WebElement, C> elementOf(
			MFinder<T, C> tableItemFinder) {
		return new ElementOfTableItemFinder<T, C>(tableItemFinder);
	}

}
