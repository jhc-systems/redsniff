package jhc.redsniff.webdriver.table.matchers;

import static java.util.Arrays.asList;
import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;

import java.util.List;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsTableConsistingOfRows extends CheckAndDiagnoseTogetherMatcher<Table> {

    private final Matcher<TableRow>[] dataRowMatchers;

    public IsTableConsistingOfRows(Matcher<TableRow>[] dataRowMatchers) {
        this.dataRowMatchers = dataRowMatchers;
    }

    public void describeTo(Description description) {
        if (dataRowMatchers.length > 0)
            description.appendList("with data rows:\n", "\n", "", asList(dataRowMatchers));
        else
            description.appendText("with no data rows");
    }

    @Override
    protected boolean matchesSafely(Table table, Description mismatchDescription) {

        return matchesBodySafely(table, mismatchDescription);
    }

    private boolean matchesBodySafely(Table table,
            Description mismatchDescription) {
        List<TableRow> dataRows = table.dataRows();
        if (dataRowMatchers.length != dataRows.size()) {
            mismatchDescription.appendText(" had " + dataRows.size() + " rows instead of " + dataRowMatchers.length
                    + "\n");
            mismatchDescription.appendText("\n" + table.toString());
            return false;
        }
        mismatchDescription.appendText(" had:");
        for (int i = 0; i < dataRowMatchers.length; i++) {
            TableRow actualRow = dataRows.get(i);
            if (!matchAndDiagnose(dataRowMatchers[i], actualRow, mismatchDescription, "\nin row "
                    + show1BasedRowNumber(i) + ":"))
                return false;
        }
        return true;
    }

    private int show1BasedRowNumber(int i) {
        return i + 1;
    }

    @SafeVarargs
    @Factory
    public static Matcher<Table> hasDataRows(Matcher<TableRow>... dataRowMatchers) {
        return new IsTableConsistingOfRows(dataRowMatchers);
    }
}
