package jhc.redsniff.webdriver.table.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.webdriver.table.matchers.IsTableRowConsistingOf.rowConsistingOf;

import java.util.List;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsTableWithHeader extends CheckAndDiagnoseTogetherMatcher<Table> {
	private final Matcher<TableRow> headerRowMatcher;

	public IsTableWithHeader(Matcher<TableRow> headerMatcher){
		this.headerRowMatcher = headerMatcher;
	}


	public void describeTo(Description description) {
		description.appendText("with header:").appendDescriptionOf(headerRowMatcher);
	}

	@Override
	protected boolean matchesSafely(Table table, Description mismatchDescription) {
	    return matchAndDiagnose(headerRowMatcher, table.headerRow(), mismatchDescription, "had header ");
	}

	@Factory 
	public  static Matcher<Table> hasHeaderRow(Matcher<TableRow> headerMatcher){
		return new IsTableWithHeader(headerMatcher);
	}

	@Factory
	public static Matcher<Table> hasHeader(List<Matcher<TableCell>> headerRowCells){
		return hasHeaderRow(rowConsistingOf(headerRowCells));
	}
	
	@Factory
	public static Matcher<Table> hasHeaders(Object... headers ){
		return hasHeaderRow(rowConsistingOf(headers));
	}
}
