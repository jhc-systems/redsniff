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


import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Predicate;


/**
 * A cell in a {@link Table}
 * @author InfanteN
 *
 */
public  class TableCell implements TableItem {
	
	protected final WebElement cellElement;
	
	//XXX not final as determined after construction
	private TableColumn column;

	//read and cached on demand
	private String cachedText;
	
	public TableCell(WebElement cellElement){
		this.cellElement = cellElement;
	}
	
	public static final Function<IndexedWebElement,TableCell> toTableCellInColumns(final List<TableColumn> columns){ 
		return new Function<IndexedWebElement, TableCell>() {
			public TableCell apply(IndexedWebElement indexedTd) {
				TableCell cell = tableCellFrom(indexedTd.webElement());
				if(indexedTd.index()>columns.size()-1) //XXX //FIXME - line up where colspans used / disallow col references
					cell.setColumn(new TableColumn("",indexedTd.index())); 
				else
					cell.setColumn(columns.get(indexedTd.index())); 
				return cell;
			}
		};
	}
	
	public static final Function<TableCell,TableColumn> TO_CONTAINING_COLUMN = new Function<TableCell, TableColumn>() {
			public TableColumn apply(TableCell input) {
			return input.getColumn();
		}
	};
	
	public TableColumn getColumn() {
		return column;
	}
	
	public void setColumn(TableColumn column) {
		this.column = column;
	}

	public static TableCell tableCellFrom(WebElement td) {
			return new TableCell(td);
	}
	

	public static Predicate<TableCell> havingAsString(final String asString) {
		return new Predicate<TableCell>() {
			public boolean apply(TableCell cell) {
				return cell.toString().equals(asString);
			}
		};
	}
	
	public WebElement element(){
		return cellElement;
	}

	public String columnDescription() {
		return column==null?"COLUMN-UNKNOWN":column.getDescription();
	}

	public String getText() {
		if(cachedText==null)
			cachedText=element().getText();
		return cachedText;
	}

	@Override
	public String toString() {
		return getText();
	}
}

