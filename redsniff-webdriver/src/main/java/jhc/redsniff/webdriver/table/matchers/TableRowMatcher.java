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