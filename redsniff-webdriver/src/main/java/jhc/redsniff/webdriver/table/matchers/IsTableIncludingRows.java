package jhc.redsniff.webdriver.table.matchers;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
/**
 * A matcher of {@link Table}s that matches if the table's data rows contain rows matching the supplied matchers
 * @see {@link IsTableRowIncluding}  , {@link IsTable}
 * @author InfanteN
 *
 */
public class IsTableIncludingRows extends CheckAndDiagnoseTogetherMatcher<Table>{
		
	List<Matcher<TableRow>> rowMatchers;
	
	public IsTableIncludingRows(List<Matcher<TableRow>> rowMatchers) {
		this.rowMatchers = rowMatchers;
	}

	public void describeTo(Description description) {
		description.appendList("containing data rows:\n", " and \n      ", "\n", rowMatchers);
	}
	
	
	@Override
	protected boolean matchesSafely(Table actualTable,	Description mismatchDescription) {
		Matching matching = new Matching(rowMatchers,actualTable);
		for(TableRow actualRow:actualTable.dataRows())
			matching.findMatchFor(actualRow);
		if(matching.isFinished())
			return true;
		else{
			mismatchDescription.appendText(" had:");
			matching.describeMismatches(mismatchDescription);
			return false;
		}
	}
	 private  class Matching {
		 private final List<Matcher<TableRow>> remainingMatchers;
	     private Map<TableRowMatcher,TableRow> mismatchedRowMapping = new LinkedHashMap<TableRowMatcher,TableRow>();
	     private Table actualTable;
	      
		public Matching(List<Matcher<TableRow>> matchers,Table actualTable) {
			this.remainingMatchers = newArrayList(matchers);
			this.actualTable = actualTable;
		}
		
		private void describeWholeTableCentredOnFirstMismatchRow(Table actualTable, Description mismatchDescription) {
			mismatchDescription.appendText("\n" + actualTable.toStringCenteredOnRow(3,firstMismatchRowIndex())); 
		}
		
		private int firstMismatchRowIndex(){
			return mismatchedRowMapping.values().iterator().next().index(); //might not actually be the first row in table 
		}

		public void describeMismatches(Description mismatchDescription) {
			for(TableRowMatcher rowInTableMatcher:mismatchedRowMapping.keySet()){
				TableRow actualRow = mismatchedRowMapping.get(rowInTableMatcher);
					describeMismatchIfMatchesIndexCell(rowInTableMatcher,actualRow,mismatchDescription);
			}
			
			if(!unmatchedMatchers().isEmpty()){
				mismatchDescription.appendText("\nCould not find any rows matching:\n ");
				for(Matcher<TableRow> unmatchedRowMatcher:unmatchedMatchers())
					mismatchDescription.appendText(">").appendDescriptionOf(unmatchedRowMatcher).appendText("\n");
			}
			describeWholeTableShowingMismatches(mismatchDescription);
			

		}

		private void describeWholeTableShowingMismatches(Description mismatchDescription) {
			
			if(!unmatchedMatchers().isEmpty()){
				mismatchDescription.appendText(actualTable.toStringShowingFirstRows(5));
			}
			else if(!mismatchedRowMapping.isEmpty())
				describeWholeTableCentredOnFirstMismatchRow(actualTable, mismatchDescription);
		}

		private void describeMismatchIfMatchesIndexCell(TableRowMatcher rowInTableMatcher, TableRow actualRow,
				Description mismatchDescription) {
			IndexCellMatcher indexedBy = rowInTableMatcher.indexedBy();
			if(indexedBy.matches(actualRow)){
				mismatchDescription.appendText("\nin row " + actualRow.index() + ":\n");
				rowInTableMatcher.describeMismatch(actualRow, mismatchDescription);//TODO could fetch stored mismatch description here
			}
			
		}

		public List<Matcher<TableRow>> unmatchedMatchers() {
			return remainingMatchers;
		}

		public void findMatchFor(TableRow actualRow) {
	        for (Matcher<TableRow>  matcher : remainingMatchers) {
	          if (matcher.matches(actualRow)) {
	        	  //we've satisfied the matcher - remove it from list of matchers
	              //TODO we could store the mismatchdescriptions at this point
	            remainingMatchers.remove(matcher);
	            return ;
	          } 
	          else if	(matchesOnIndexCells(actualRow, matcher)){
	        	  mismatchedRowMapping.put((TableRowMatcher) matcher, actualRow);
	          		remainingMatchers.remove(matcher);
	          		return;
	          }
	        
	        }
	        //we've gone through all row matchers and not matched this row. That's ok - we only care about satisfying all our matchers but keep note of it 
	        //for figuring out failure messages - ie figuring out which of the unmatched rows goes with which unmatched row-matcher.
	    }

	
		private boolean matchesOnIndexCells(TableRow actualRow,
				Matcher<TableRow> matcher) {
			if(matcher instanceof TableRowMatcher){
					TableRowMatcher rowInTableMatcher = (TableRowMatcher)matcher;
					IndexCellMatcher indexedBy = rowInTableMatcher.indexedBy();
					return indexedBy.matches(actualRow);
			}
			return false;
		}

		public boolean isFinished() {
	      return remainingMatchers.isEmpty() && mismatchedRowMapping.isEmpty();
	      }
	
	      
	 }
	
	

	@SafeVarargs
	@Factory
	public static Matcher<Table> hasDataRowsIncluding(Matcher<TableRow>... dataRowMatchers) {
		return new IsTableIncludingRows(asList(dataRowMatchers));
	}
}