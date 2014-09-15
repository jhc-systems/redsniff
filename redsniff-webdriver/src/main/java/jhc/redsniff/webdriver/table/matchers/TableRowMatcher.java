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
package jhc.redsniff.webdriver.table.matchers;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static jhc.redsniff.html.tables.TableCell.TO_CONTAINING_COLUMN;

import java.util.Set;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableColumn;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;

public abstract class TableRowMatcher extends CheckAndDiagnoseTogetherMatcher<TableRow> {

	public static void describeWholeRowHighlightingMismatchCells(TableRow actualRow, Description mismatchDescription,Set<TableColumn> mismatchedColumns) {
		mismatchDescription.appendText("\nActual row:\n");
		mismatchDescription.appendText(actualRow.toStringHighlightingCellsInColumns(mismatchedColumns));
	}

	protected static Set<TableColumn> columnsOf(Iterable<TableCell> matchedColumnButNotContents) {
		return newLinkedHashSet(transform(matchedColumnButNotContents, TO_CONTAINING_COLUMN));
	}

//	public static void describeMismatchFor(TableRow actualRow, Description mismatchDescription, Matcher<TableCell> cellContentsMatcher,TableCell mismatchedCell){
//		describeMismatchFor(actualRow,mismatchDescription,cellContentsMatcher,Arrays.asList(mismatchedCell),Matchers.any(TableColumn.class));
//	}
	//not currently used - 
//	public static void describeMismatchFor(TableRow actualRow, Description mismatchDescription, Matcher<TableCell> cellContentsMatcher,List<TableCell> mismatchedCells,Matcher<TableColumn> columnMatcher) {
//		switch(mismatchedCells.size()){
//		case 0:
//			mismatchDescription.appendText("No column on row was ").appendDescriptionOf(columnMatcher);
//			break;
//		case 1://TODO - some of this is repeated in IsTableRowIncluding#describeMismatchIfCellIsForColumn - could de-dupe?
//			TableCell actualCell = getFirst(mismatchedCells,null);
//			mismatchDescription
//				.appendText("cell in column: ")
//				.appendText(actualCell.columnDescription())
//				.appendText(", expected to be ")
//				.appendDescriptionOf(cellContentsMatcher)
//				.appendText("\n");
//			cellContentsMatcher.describeMismatch(actualCell, mismatchDescription);
//			describeWholeRowHighlightingMismatchCells(actualRow, mismatchDescription, columnsOf(mismatchedCells));
//			break;
//		default:
//			mismatchDescription.appendText("Several columns matched ").appendDescriptionOf(columnMatcher).appendText(" but none were ").appendDescriptionOf(cellContentsMatcher);
//			describeWholeRowHighlightingMismatchCells(actualRow, mismatchDescription, columnsOf(mismatchedCells));
//			break;
//		}
//	}

	public abstract IndexCellMatcher indexedBy();
}