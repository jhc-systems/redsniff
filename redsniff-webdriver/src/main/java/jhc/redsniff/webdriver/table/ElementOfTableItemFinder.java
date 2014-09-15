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
