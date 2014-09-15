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
package jhc.redsniff.html.tables;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.transform;
import static jhc.redsniff.html.tables.IndexedWebElement.indexedElements;
import static jhc.redsniff.html.tables.TableCell.toTableCellInColumns;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * Represents a row in a  {@link Table} (snapshot). Consists of {@link TableCell}s
 * Note that the list of cells is a 'view' built using {@link Lists#transform(List, com.google.common.base.Function)} which uses lazy evaluation
 * @author InfanteN
 *
 */
public class TableRow implements TableItem {
	private static final String SEPARATOR = "|";
	
	private final WebElement rowElement;
	private final List<TableCell> cells ;
	private final int index;
	
	
	public TableRow(List<TableCell> cells,WebElement rowElement, int index){
		this.cells = cells;
		this.rowElement = rowElement;
		this.index = index;
	}
	
	/**
	 * Build a {@link TableRow} from a data/body tr {@link WebElement} - assigning columns from supplied columns
	 * @param tr
	 * @param columns
	 * @return a {@link TableRow}
	 */
	public static TableRow dataRowFrom(IndexedWebElement tr, List<TableColumn> columns) {
		//will lazily build cells only when the list is iterated through
		List<TableCell> cells=transform(dataItemsOnRow(tr.webElement()),TableCell.toTableCellInColumns(columns));
		return new TableRow(cells,tr.webElement(),tr.index() +1); // use 1-based indexing 
	}
	
	public TableRow withColumns(List<TableColumn> columns){
		return this; //TODO - return new TableRow with items having columns set
	}
	/**
	 * Build a {@link TableRow} from a header tr {@link WebElement} -
	 * @param tr
	 * @return a {@link TableRow}
	 */
	public static TableRow headerRowFrom(WebElement tr) {
		List<TableCell> headerCells = transform(headerItemsOnRow(tr),TO_TABLE_CELL);
		return new TableRow(headerCells,tr,-1);
	}
	
	/**
	 * Build a {@link TableRow} from a header tr {@link WebElement} -
	 * @param tr
	 * @return a {@link TableRow}
	 */
	public static TableRow headerRowWithColumns(TableRow headerRow, final List<TableColumn> columns) {
		List<TableCell> headerCells = transform(headerItemsOnRow(headerRow.element()),toTableCellInColumns(columns));
		return new TableRow(headerCells,headerRow.element(),headerRow.index());
	}
	
	private static final Function<IndexedWebElement,TableCell> TO_TABLE_CELL = new Function<IndexedWebElement, TableCell>() {
		public TableCell apply(IndexedWebElement tdIndexed) {
			return TableCell.tableCellFrom(tdIndexed.webElement());
		}
	};
	/**
	 * 
	 * @return the tr {@link WebElement} that this was built from
	 */
	public WebElement element(){
		return rowElement;
	}

	public static final Function<IndexedWebElement,TableRow> toTableRowWithColumns(final List<TableColumn> columns){
		return new Function<IndexedWebElement, TableRow>() {
			public TableRow apply(IndexedWebElement tr) {
				return dataRowFrom(tr,columns);
			}
		};
	}
		
	@Override 
	public String toString() {
		return join(cells);
	}

	public String join(List<?> list) {
		return Joiner.on(SEPARATOR).join(list);
	}
	
	

	private String highlight(TableCell cell) {
		return " <!***" + cell.toString() + "***!>";
	}

	public TableCell cell(int i){
		return cells.get(i);
	}

	public List<TableCell> cells() {
		return cells;
	}
	
	private static List<IndexedWebElement> dataItemsOnRow(WebElement tr) {
		return indexedElements(tr.findElements(By.tagName("td")));
	}
	
	private static List<IndexedWebElement> headerItemsOnRow(WebElement tr) {
		return indexedElements(tr.findElements(By.tagName("th")));
	}

	
	public String toStringHighlightingCellsInColumns(Set<TableColumn> highlightCellColumns) {
		switch(highlightCellColumns.size()){
		case 0:
			return toString();
		case 1:
			int columnIndex = getLast(highlightCellColumns).getIndex();
			return join(cells.subList(0, columnIndex)) + highlight(cells.get(columnIndex)) + " ... [rest omitted]";
		default:
			List<String> cellStrings = new ArrayList<String>(cells.size());
			for(TableCell cell:cells()){
				if(highlightCellColumns.contains(cell.getColumn()))
					cellStrings.add(highlight(cell));
				else
					cellStrings.add(cell.toString());
			}
			return join(cellStrings);	
		}
	}

	public TableCell cellInColumn(TableColumn column) {
		return cell(column.getIndex());
	}

	public int index() {
		return index;
	}

	
}


















