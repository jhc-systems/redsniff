package jhc.redsniff.webdriver.table.matchers;

import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.webdriver.table.matchers.IsTableCellWithText.TO_CELL_MATCHER;

import java.util.List;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
/**
 * A matcher of {@link TableRows} that matches if each cell matcher matches the respective cell
 * @see {@link IsTableRowIncluding}  
 * @author InfanteN
 *
 */
public class IsTableRowConsistingOf extends TableRowMatcher{


    private List<Matcher<TableCell>> cellContentsMatchers;

    public IsTableRowConsistingOf(List<Matcher<TableCell>> cellMatchers){
        this.cellContentsMatchers = cellMatchers;
    }

    public void describeTo(Description description) {
        for(Matcher<TableCell> cellMatcher:cellContentsMatchers)
            description.appendDescriptionOf(cellMatcher).appendText(",");
    }

    @Override
    protected boolean matchesSafely(TableRow actualRow, Description mismatchDescription) {
        if(cellContentsMatchers.size()!=actualRow.cells().size()){
            mismatchDescription.appendText("with " + actualRow.cells().size() + " cells instead of " + cellContentsMatchers.size()+":\n");
            mismatchDescription.appendText("Actual Row:\n" +actualRow.toString());
            return false;
        }

        for(int i=0;i<cellContentsMatchers.size();i++){
            TableCell actualCell = actualRow.cell(i);
            Matcher<TableCell> cellMatcher = cellContentsMatchers.get(i);
            if(!matchAndDiagnose(cellMatcher, actualCell, mismatchDescription,
                  newDescription()
                    .appendText("cell in column: ")
                    .appendText(actualCell.columnDescription())
                    .appendText(", expected to be ")
                    .appendDescriptionOf(cellMatcher).toString()))
                return false;
        }
        return true;
    }





    //has to accept Object since we might want to mix and match Strings and cell matchers
    @SuppressWarnings("unchecked")
    @Factory
    public	static Matcher<TableRow> rowConsistingOf(Object... fields){

        if(fields.length==1 && fields[0] instanceof List )
            return rowConsistingOfList((List<Matcher<TableCell>>) fields[0]);
        else {
            checkFieldTypes(fields);
            return rowConsistingOfList(transform(asList(fields),TO_CELL_MATCHER));
        }
    }



    private static void checkFieldTypes(Object[] fields) {
        for(Object field:fields){
            if(field == null)
                throw new IllegalArgumentException("rowConsistingOf can only accept Strings or TableCell matchers - received null");
            if(!(field instanceof String|| field instanceof Matcher) )
                throw new IllegalArgumentException("rowConsistingOf can only accept Strings or TableCell matchers - received " + field.getClass().getName() + field.toString());
        }
    }

    private	static Matcher<TableRow> rowConsistingOfList(List<Matcher<TableCell>> cellMatcherList){
        return new IsTableRowConsistingOf(cellMatcherList);
    }

    @Override
    public IndexCellMatcher indexedBy() {
        return new IndexCellMatcher(cellContentsMatchers.subList(0, 1)); // default to index on the first column mentioned in the expectation - not ideal for this kind of search
    }

}