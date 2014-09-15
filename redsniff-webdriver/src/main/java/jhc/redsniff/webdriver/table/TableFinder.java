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

import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.internal.core.Transformer;
import jhc.redsniff.internal.finders.TransformingMFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class TableFinder extends TransformingMFinder<WebElement, Table,SearchContext> {

	 public TableFinder(MFinder<WebElement, SearchContext> tableElementFinder) {
		super(tableElementFinder, TO_TABLE);
	}

	private static final Transformer<WebElement, Table> TO_TABLE = new Transformer<WebElement, Table>() {
		@Override
		public Table transform(WebElement tableElement, Description couldNotTransformDescription) {
			try {
                return Table.fromTableElement(tableElement);
            } catch (Exception e) {
               couldNotTransformDescription.appendText(" could not transform to Table " + e.getMessage());
               return null;
            }
		}
		@Override
		public void describeTo(Description description) {
			description.appendText("table object");			
		}
	};

	@Factory
	    public static MFinder<Table, SearchContext> table(MFinder<WebElement, SearchContext> tableElementFinder) {
	        return new TableFinder(tableElementFinder);
	    }
}
