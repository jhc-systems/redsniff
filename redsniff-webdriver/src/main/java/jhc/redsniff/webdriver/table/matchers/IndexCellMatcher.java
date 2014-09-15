package jhc.redsniff.webdriver.table.matchers;

import java.util.List;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IndexCellMatcher extends IsTableRowIncluding {

    public IndexCellMatcher(List<Matcher<TableCell>> indexCellMatchers) {
        super(indexCellMatchers);
    }

    @Override
    protected boolean matchesSafely(TableRow actualRow,
            Description mismatchDescription) {
        return doMatching(actualRow, mismatchDescription).isFinished();
    }
}
