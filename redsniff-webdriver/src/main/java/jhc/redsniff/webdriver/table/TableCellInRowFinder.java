package jhc.redsniff.webdriver.table;

import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.LocatorFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class TableCellInRowFinder extends LocatorFinder<TableCell, TableRow>{

	public TableCellInRowFinder() {
        super(createLocator());
    }

    private static Locator<TableCell, TableRow> createLocator() {
        return new Locator<TableCell, TableRow>(){

            @Override
            public void describeLocatorTo(Description description) {
                description.appendText("cell");
            }

            @Override
            public CollectionOf<TableCell> findElementsFrom(TableRow row) {
               return CollectionOf.collectionOf(row.cells());
            }

            @Override
            public String nameOfAttributeUsed() {
                   return "CELLS";
            }
            
        };
    }

   

	@Factory
	public static MFinder<TableCell, TableRow> cell(){
		return new TableCellInRowFinder();
	}
}
