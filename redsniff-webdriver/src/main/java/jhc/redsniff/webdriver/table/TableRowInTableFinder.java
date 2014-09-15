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

import static jhc.redsniff.internal.core.CollectionOf.collectionOf;
import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.LocatorFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;


public class TableRowInTableFinder extends LocatorFinder<TableRow, Table> {

	public TableRowInTableFinder() {
        super(createLocator());
    }

    private static Locator<TableRow, Table> createLocator() {
        return new Locator<TableRow, Table>() {

            @Override
            public void describeLocatorTo(Description description) {
                description.appendText("data row");
            }

            @Override
            public CollectionOf<TableRow> findElementsFrom(Table table) {
                return collectionOf(table.dataRows());
            }

            @Override
            public String nameOfAttributeUsed() {
               return "DATAROWS";
            }
        };
    }

	@Factory
	public static MFinder<TableRow, Table> row(){
		return new TableRowInTableFinder();
	}
}
