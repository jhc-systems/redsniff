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

import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class TableColumn {
	
	private final String columnHeader;
	private final int index;

	public TableColumn(String columnHeader, int index) {
		this.columnHeader = columnHeader;
		this.index = index;
	}
	
	public String getColumnHeader() {
		return columnHeader;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getDisplayIndex() {
		return index+1;
	}
	
	//Lazily-evaluate a column's cells - is it worth doing this and being inconsistent? could do either always like this, for rows etc, or never.
	public List<TableCell> cellsInTable(Table table) {
		return transform(table.dataRows(),toCellInThisColumn());
	}

	public static List<TableColumn> columnsFrom(TableRow headerRow) {
		List<TableColumn> columns = new ArrayList<TableColumn>(headerRow.cells().size());
		int index=0;
		for(TableCell headerCell:headerRow.cells()) {
			TableColumn column = new TableColumn(headerCell.toString(), index++);
			headerCell.setColumn(column);
			columns.add(column);
		}
		return columns;
	}
	
	private Function<TableRow, TableCell> toCellInThisColumn() {
		return new Function<TableRow, TableCell>() {
			public TableCell apply(TableRow row) {
				return row.cellInColumn(TableColumn.this);
			}
		};
	}
	
	public static Predicate<TableColumn> havingColumnHeader(final String headerText) {
		return new Predicate<TableColumn>() {
			public boolean apply(TableColumn column) {
				return column.getColumnHeader().equals(headerText);
			}
		};
	}

	public String getDescription() {
		
		String headerDescription = columnHeader==null||columnHeader.trim().isEmpty()
									?" (with blank header)"
									:"headed <" + columnHeader+ ">";
		return "[" + getDisplayIndex() + "] , " + headerDescription ;
	}
	
	public String toString(){
		return getDescription();
	}
}
