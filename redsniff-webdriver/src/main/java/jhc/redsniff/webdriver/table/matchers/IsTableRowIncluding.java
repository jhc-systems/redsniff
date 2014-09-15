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

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newArrayList;
import static jhc.redsniff.webdriver.table.matchers.IsTableCellInColumn.isInColumnThat;
import static jhc.redsniff.webdriver.table.matchers.IsTableColumn.isColumnIdentifiedBy;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableColumn;
import jhc.redsniff.html.tables.TableRow;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher of {@link TableRow}s that matches if the row contains the supplied
 * cell in the given column
 * 
 * @see {@link IsTableRowConsistingOf}
 * @author InfanteN
 * 
 */
public class IsTableRowIncluding extends TableRowMatcher {

    List<Matcher<TableCell>> cellMatchers;
    private String matcherTextToUseForDescription;

    protected IsTableRowIncluding(List<Matcher<TableCell>> cellMatchers) {
        this(cellMatchers, "includes cells");
    }

    private IsTableRowIncluding(List<Matcher<TableCell>> cellMatchers, String matcherTextToUseForDescription) {
        this.cellMatchers = cellMatchers;
        this.matcherTextToUseForDescription = matcherTextToUseForDescription;
    }

    public void describeTo(Description description) {
        description.appendList(matcherTextToUseForDescription + ": ", " and ", "", cellMatchers);
    }

    @Override
    protected boolean matchesSafely(TableRow actualRow, Description mismatchDescription) {
        Matching matching = doMatching(actualRow, mismatchDescription);

        if (matching.isFinished())
            return true;
        else {
            matching.describeMismatchesTo(mismatchDescription);
            matching.describeWholeRowHighlightingMismatchCellsTo(mismatchDescription);
            return false;
        }
    }

    protected Matching doMatching(TableRow actualRow,
            Description mismatchDescription) {
        Matching matching = new Matching(cellMatchers, actualRow, mismatchDescription);
        for (TableCell actualCell : actualRow.cells())
            matching.findMatchFor(actualCell);
        return matching;
    }

    protected class Matching {
        private final List<Matcher<TableCell>> remainingMatchers;
        private final TableRow actualRow;
        private Map<CellInColumnMatcher, TableCell> mismatchedColumnMapping = new LinkedHashMap<CellInColumnMatcher, TableCell>();

        public Matching(List<Matcher<TableCell>> matchers, TableRow actualRow, Description mismatchDescription) {
            this.remainingMatchers = newArrayList(matchers);
            this.actualRow = actualRow;
        }

        public void describeWholeRowHighlightingMismatchCellsTo(Description mismatchDescription) {
            TableRowMatcher.describeWholeRowHighlightingMismatchCells(actualRow, mismatchDescription,
                    columnsOf(mismatchedColumnMapping.values()));
        }

        public void describeMismatchesTo(Description mismatchDescription) {
            for (CellInColumnMatcher cellInColumnMatcher : mismatchedColumnMapping.keySet()) {
                TableCell actualCell = mismatchedColumnMapping.get(cellInColumnMatcher);
                cellInColumnMatcher.describeMismatchIfCellIsForColumn(actualCell, mismatchDescription);
            }
            if (!unmatchedMatchersForUnknownColumn().isEmpty())
                describeUnmatchedColumnsTo(mismatchDescription);
        }

        private void describeUnmatchedColumnsTo(Description mismatchDescription) {
            mismatchDescription.appendText("\nNot matched:");
            for (Matcher<TableCell> unmatchedCellMatcher : unmatchedMatchersForUnknownColumn()) {
                mismatchDescription.appendDescriptionOf(unmatchedCellMatcher);
                if (unmatchedCellMatcher instanceof CellInColumnMatcher) {
                    mismatchDescription.appendText(" -  No column matched: ");
                    ((CellInColumnMatcher) unmatchedCellMatcher).columnMatcher.describeTo(mismatchDescription);

                }
            }
            mismatchDescription.appendText("\nColumn headings were:\n");
            mismatchDescription.appendText(on(", ").join(columnsOf(actualRow.cells())));
        }

        public List<Matcher<TableCell>> unmatchedMatchersForUnknownColumn() {
            return remainingMatchers;
        }

        public void findMatchFor(TableCell actualCell) {
            for (Matcher<TableCell> matcher : remainingMatchers) {
                if (matcher.matches(actualCell)) {
                    // TODO could store mismatch descriptions here
                    // we've satisfied the matcher - remove it from list of
                    // matchers
                    remainingMatchers.remove(matcher);
                    return;
                }
                else if (canMatchToAColumn(actualCell, matcher)) {
                    mismatchedColumnMapping.put((CellInColumnMatcher) matcher, actualCell);
                    remainingMatchers.remove(matcher);
                    return;
                }

            }
            // we've gone through all matchers and not matched this cell. That's
            // ok - we only care about satisfying all our matchers but keep note
            // of it
            // for figuring out failure messages - ie figuring out which of the
            // unmatched cells goes with which unmatched matcher.
        }

        private boolean canMatchToAColumn(TableCell actualCell, Matcher<TableCell> matcher) {
            if (matcher instanceof CellInColumnMatcher) {
                return ((CellInColumnMatcher) matcher).matchesColumnButNotContents(actualCell);
            }
            return false;
        }

        public boolean isFinished() {
            return remainingMatchers.isEmpty() && mismatchedColumnMapping.isEmpty();
        }
    }

    private static class CellInColumnMatcher extends TypeSafeMatcher<TableCell> {
        private Matcher<TableColumn> columnMatcher;
        private Matcher<TableCell> cellContentsMatcher;

        public CellInColumnMatcher(Matcher<TableColumn> columnMatcher, Matcher<TableCell> cellContentsMatcher) {
            this.columnMatcher = columnMatcher;
            this.cellContentsMatcher = cellContentsMatcher;
        }

        public void describeTo(Description description) {
            description.appendDescriptionOf(cellContentsMatcher).appendText(" in column ")
                    .appendDescriptionOf(columnMatcher);
        }

        public boolean matchesColumn(TableCell actualCell) {
            return isInColumnThat(columnMatcher).matches(actualCell);
        }

        public boolean matchesContents(TableCell actualCell) {
            return cellContentsMatcher.matches(actualCell);
        }

        public boolean matchesColumnButNotContents(TableCell actualCell) {
            return matchesColumn(actualCell) && !matchesContents(actualCell);
        }

        @Override
        protected boolean matchesSafely(TableCell actualCell) {
            return matchesColumn(actualCell) && matchesContents(actualCell);
        }

        public void describeMismatchIfCellIsForColumn(TableCell actualCell, Description mismatchDescription) {
            if (matchesColumnButNotContents(actualCell)) {
                mismatchDescription
                        .appendText("cell in column: ")
                        .appendText(actualCell.columnDescription())
                        .appendText(", expected to be ")
                        .appendDescriptionOf(cellContentsMatcher)
                        .appendText("\n");
                cellContentsMatcher.describeMismatch(actualCell, mismatchDescription);
              //TODO could fetch back mismatch descriptions here
            }
        }
    }

    @Factory
    public static Matcher<TableCell> valueInColumn(Object columnIdentifier, String cellContents) {
        return new CellInColumnMatcher(IsTableColumn.isColumnIdentifiedBy(columnIdentifier), IsTableCellWithText
                .cellWithText(cellContents));
    }

    @Factory
    public static Matcher<TableCell> valueInColumn(Object columnIdentifier, Matcher<TableCell> cellMatcher) {
        return new CellInColumnMatcher(isColumnIdentifiedBy(columnIdentifier), cellMatcher);
    }

    @SafeVarargs
    @Factory
    public static Matcher<TableRow> includesCells(Matcher<TableCell>... included) {
        return new IsTableRowIncluding(Arrays.asList(included));
    }

    @SafeVarargs
    @Factory
    public static Matcher<TableRow> rowIncluding(Matcher<TableCell>... included) {
        return new IsTableRowIncluding(Arrays.asList(included), "Row including");
    }

    @Override
    public IndexCellMatcher indexedBy() {
        return new IndexCellMatcher(cellMatchers.subList(0, 1)); 
     // default to index on the first column mentioned in the expectation
    }

}