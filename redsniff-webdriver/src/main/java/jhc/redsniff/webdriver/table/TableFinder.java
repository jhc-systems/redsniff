package jhc.redsniff.webdriver.table;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.internal.core.Transformer;
import jhc.redsniff.internal.finders.TransformingMFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class TableFinder extends TransformingMFinder<WebElement, Table,SearchContext> {

	 public TableFinder(MFinder<WebElement, SearchContext> tableElementFinder) {
		super(tableElementFinder, TO_TABLE);
	}

	private static final Transformer<WebElement, Table> TO_TABLE = new Transformer<WebElement, Table>() {
		@Override
		public Table transform(WebElement tableElement, Description couldNotTransformDescription) {
			try {
                return Table.fromTableElement(tableElement);
            } catch (Exception e) {
               couldNotTransformDescription.appendText(" could not transform to Table " + e.getMessage());
               return null;
            }
		}
		@Override
		public void describeTo(Description description) {
			description.appendText("table object");			
		}
	};

	@Factory
	    public static MFinder<Table, SearchContext> table(MFinder<WebElement, SearchContext> tableElementFinder) {
	        return new TableFinder(tableElementFinder);
	    }
}
