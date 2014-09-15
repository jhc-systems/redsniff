package jhc.redsniff.webdriver.table;

import static jhc.redsniff.internal.core.CollectionOf.collectionOf;
import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.LocatorFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;


public class TableRowInTableFinder extends LocatorFinder<TableRow, Table> {

	public TableRowInTableFinder() {
        super(createLocator());
    }

    private static Locator<TableRow, Table> createLocator() {
        return new Locator<TableRow, Table>() {

            @Override
            public void describeLocatorTo(Description description) {
                description.appendText("data row");
            }

            @Override
            public CollectionOf<TableRow> findElementsFrom(Table table) {
                return collectionOf(table.dataRows());
            }

            @Override
            public String nameOfAttributeUsed() {
               return "DATAROWS";
            }
        };
    }

	@Factory
	public static MFinder<TableRow, Table> row(){
		return new TableRowInTableFinder();
	}
}
