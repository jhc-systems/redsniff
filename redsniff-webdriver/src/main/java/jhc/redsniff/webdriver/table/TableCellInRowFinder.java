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

import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.LocatorFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class TableCellInRowFinder extends LocatorFinder<TableCell, TableRow>{

	public TableCellInRowFinder() {
        super(createLocator());
    }

    private static Locator<TableCell, TableRow> createLocator() {
        return new Locator<TableCell, TableRow>(){

            @Override
            public void describeLocatorTo(Description description) {
                description.appendText("cell");
            }

            @Override
            public CollectionOf<TableCell> findElementsFrom(TableRow row) {
               return CollectionOf.collectionOf(row.cells());
            }

            @Override
            public String nameOfAttributeUsed() {
                   return "CELLS";
            }
            
        };
    }

   

	@Factory
	public static MFinder<TableCell, TableRow> cell(){
		return new TableCellInRowFinder();
	}
}
