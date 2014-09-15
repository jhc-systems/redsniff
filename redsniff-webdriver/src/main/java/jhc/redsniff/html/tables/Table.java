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

import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.transform;
import static jhc.redsniff.html.tables.IndexedWebElement.indexedElements;
import static jhc.redsniff.html.tables.TableCell.havingAsString;
import static jhc.redsniff.html.tables.TableColumn.columnsFrom;
import static jhc.redsniff.html.tables.TableColumn.havingColumnHeader;
import static jhc.redsniff.html.tables.TableRow.*;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
 
/**
 * Represents a snapshot of an html data table with a header row and List of data rows. 
 * Note that the list of rows is a 'view' built using {@link Lists#transform(List, com.google.common.base.Function)} which uses lazy evaluation
 * so the initial parsing does not take time, but if iterating through all rows then only then will it call all the apply methods to get the contents from the web page
 * this means assertions can be checked without necessarily parsing the whole table -only as much as necessary to make the assertion
 * @author InfanteN
 *
 */
public final class Table implements TableItem{

	private final TableRow headerRow;
	private final List<TableRow> dataRows;
	private final List<TableColumn> columns;
	private final String label;
	
	private final WebElement tableElement;
	
	
	private Table(String label,WebElement tableElement,TableRow headerRow,List<TableRow> dataRows,List<TableColumn> columns){
		this.label = label;
		this.headerRow = headerRow;
		this.dataRows = dataRows;
		this.columns = columns;
		this.tableElement = tableElement;
	}
	

	@Override
	public WebElement element() {
		return tableElement;
	}
	
	public static Table fromTableElement(WebElement tableElement){
		return fromTableElement(null, tableElement);
	}

	public static Table fromTableElement(String label,WebElement tableElement){
		
		TableRow headerRow= headerRowFrom(headerRowElementUnder(tableElement));
		List<TableColumn> columns = columnsFrom(headerRow);
		headerRow = headerRowWithColumns(headerRow,columns);
		//FIXME - now setting columns on headers but would be neater not to have to read the tr twice
		List<IndexedWebElement> dataRowElements = dataRowElementsUnder(tableElement);
		//will lazily build rows only when the list is iterated through
		List<TableRow> dataRows = transform(dataRowElements,toTableRowWithColumns(columns));
		return new Table(label,tableElement,headerRow,dataRows,columns);
	}
		
	public TableRow headerRow() {
		return headerRow;
	}
	
	public List<TableRow> dataRows() {
		return dataRows;
	}
	
	public List<TableColumn> columns(){
		return columns;
	}
	
	public String label(){
		return label;
	}
	
	public String toString(){
		return toStringShowingFirstRows(5);
	}
	
	public String toStringShowingFirstRows(int numberOfRows){
		return toStringCenteredOnRow(numberOfRows,0);
	}
	
	public String toStringCenteredOnRow(int numberOfRows, int centeredOnRow){
		String headerRowStr = headerRow.toString();
		String divider = leftPad("", headerRowStr.length(), "-");
		return "Table: " + Strings.nullToEmpty(label) + "\n" + 
				headerRowStr + "\n" +
				divider + "\n"  +
				dataRowsCenteredOn(numberOfRows, centeredOnRow);
	}
	
	
	private String dataRowsCenteredOn(int numberOfRows,int centeredOnRow){
		int lastRowIndex = dataRows.size()-1;
		int startRow = Math.max(centeredOnRow-(numberOfRows/2), 0);
		int endRow = Math.min(startRow+numberOfRows-1,lastRowIndex);
		int numStartSkipped=startRow;
		
		int numEndSkipped=lastRowIndex-endRow;
		String startDotDot = numStartSkipped>0?"...["+numStartSkipped+" rows]...\n":"";
		String endDotDot = numEndSkipped>0?"\n...["+numEndSkipped+" more rows]...\n":"";
		return startDotDot + 
				Joiner.on("\n").join(dataRows.subList(startRow, endRow+1))+
				endDotDot;
		
	}
				
	public TableRow rowWithValueInColumn(TableColumn column,String value){
		int indexOfDesiredRow = indexOf(column.cellsInTable(this),havingAsString(value));
		if(indexOfDesiredRow<0)
			throw new AssertionError("No row had value" + value  + " in column " + column.getDescription());
		return dataRows.get(indexOfDesiredRow);
	}
	
	public TableColumn columnWithHeader(final String headerText)  {
		TableColumn column = getFirst(filter(columns,havingColumnHeader(headerText)), null);
		if(column==null)
			throw new AssertionError("No column with header '"+headerText+"' found");
		return column;
	}

	public TableRow rowWithValueInColumn(String headerText, String value) {
		return rowWithValueInColumn(columnWithHeader(headerText), value);
	}
	

	private static WebElement headerRowElementUnder(WebElement tableElement){
		return tableElement.findElement(By.cssSelector("thead tr"));
	}
			
	private static List<IndexedWebElement> dataRowElementsUnder(WebElement tableElement){
		return indexedElements(tableElement.findElements(By.cssSelector("tbody tr")));
	}

}
