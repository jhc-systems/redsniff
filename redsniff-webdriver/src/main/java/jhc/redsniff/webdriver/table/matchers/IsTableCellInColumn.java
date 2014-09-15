package jhc.redsniff.webdriver.table.matchers;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableColumn;
import jhc.redsniff.internal.matchers.FeatureMatcher;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsTableCellInColumn extends FeatureMatcher<TableCell, TableColumn> {
	public IsTableCellInColumn(Matcher<? super TableColumn> subMatcher) {
		super(subMatcher, "is in column", "column");
	}

	@Override
	protected TableColumn featureValueOf(TableCell actual) {
		return actual.getColumn();
	}
	
	@Factory
	public static Matcher<TableCell>  isInColumnThat(Matcher<TableColumn> columnMatcher){
		return new IsTableCellInColumn(columnMatcher);
	}
}