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
package jhc.redsniff.webdriver;

import static jhc.redsniff.internal.finders.OnlyFinder.only;
import static jhc.redsniff.internal.matchers.ContainsIgnoringWhitespace.containsIgnoringWhitespace;
import static jhc.redsniff.webdriver.Finders.checkbox;
import static jhc.redsniff.webdriver.Finders.elementOf;
import static jhc.redsniff.webdriver.Finders.image;
import static jhc.redsniff.webdriver.Finders.link;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasAttribute;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasDataRows;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasDataRowsIncluding;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasHeaders;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasSubElement;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import static jhc.redsniff.webdriver.WebDriverMatchers.isSelected;
import static jhc.redsniff.webdriver.WebDriverMatchers.isTableElementThat;
import static jhc.redsniff.webdriver.WebDriverMatchers.isUnselected;
import static jhc.redsniff.webdriver.WebDriverMatchers.rowConsistingOf;
import static jhc.redsniff.webdriver.WebDriverMatchers.rowIncluding;
import static jhc.redsniff.webdriver.finders.HtmlTagFinders.tableElement;
import static jhc.redsniff.webdriver.table.TableCellInRowFinder.cell;
import static jhc.redsniff.webdriver.table.TableFinder.table;
import static jhc.redsniff.webdriver.table.TableRowInTableFinder.row;
import static jhc.redsniff.webdriver.table.matchers.IsTableCellContaining.cellContaining;
import static jhc.redsniff.webdriver.table.matchers.IsTableCellInColumn.isInColumnThat;
import static jhc.redsniff.webdriver.table.matchers.IsTableCellWithText.cellWithText;
import static jhc.redsniff.webdriver.table.matchers.IsTableColumn.isColumnIdentifiedBy;
import static jhc.redsniff.webdriver.table.matchers.IsTableRowIncluding.includesCells;
import static jhc.redsniff.webdriver.table.matchers.IsTableRowIncluding.valueInColumn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import jhc.redsniff.RedsniffTestBase;
import jhc.redsniff.html.tables.TableCell;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.number.OrderingComparison;
import org.junit.Test;
import org.openqa.selenium.WebElement;

//TODO split this up into smaller test classes by utility being tested
public class WebTesterTableTest extends RedsniffTestBase {

	
	private static final Matcher<TableCell> EMPTY_HEADER = cellWithText(isEmptyOrNullString());


	
	@Test
	public void 
	assertsTableRow(){
		WebElement table = t.find(only(tableElement().that(hasName("a_table"))));
		
	assertThat(table, isTableElementThat(hasHeaders("Stock Code","Stock Description","Price","Country")));
		
	assertThat(table, isTableElementThat(hasDataRows(
			rowIncluding(valueInColumn("Stock Code","VOD.L"),valueInColumn("Price","1.23")),
			rowIncluding(valueInColumn("Stock Description","Marks And Spencer"),valueInColumn("Price","4.56")),
			rowIncluding(valueInColumn(4,"GB")),
			rowIncluding(valueInColumn("Country","US"))
			)));
	}
	
	@Test
	public void 
	complainsWithExpectationDescriptionWhenDifferentNumberOfRowsThanAsserted(){
	thrown.expectMessage(containsIgnoringWhitespace("Expected:  a table with data rows:\n"
			+ "Row including: \"VOD.L\" in column <Stock Code> and \"1.23\" in column <Price>\n"
			+ "Row including: \"Marks And Spencer\" in column <Stock Description> and \"4.56\" in column <Price>\n"
			+ "Row including: \"GB\" in column [<4>]\n"
			+ "Row including: \"US\" in column <Country>\n"
			+ "Row including: \"NOTTHERE\" in column <Stock Code>\n"
			+ "     but:  table  had 4 rows instead of 5\n"
			+ "\n"
			+ "Table: \n"
			+ "Stock Code|Stock Description|Price|Country\n" 
			+ "------------------------------------------\n" 
			+ "VOD.L|Vodafone|1.23|GB\n"
			+ "MKS.L|Marks And Spencer|4.56|GB\n"
			+ "TSCO.L|Tesco|7.89|GB\n"
			+ "MCD.L|McDonalds|7.89|US"));
	
	WebElement table = t.find(only(tableElement().that(hasName("a_table"))));
		
	assertThat(table,isTableElementThat(hasHeaders("Stock Code","Stock Description","Price","Country")));
		
	assertThat(table,isTableElementThat(hasDataRows(
			rowIncluding(valueInColumn("Stock Code","VOD.L"),valueInColumn("Price","1.23")),
			rowIncluding(valueInColumn("Stock Description","Marks And Spencer"),valueInColumn("Price","4.56")),
			rowIncluding(valueInColumn(4,"GB")),
			rowIncluding(valueInColumn("Country","US")),
			rowIncluding(valueInColumn("Stock Code","NOTTHERE"))
			)));
	}
	
	@Test
	public void 
	complainsWithDetailOfUnMatchedRowWhenNotMatched(){
	thrown.expectMessage(containsIgnoringWhitespace(
			"Expected:  a table with data rows:\n" +
			"Row including: \"VOD.L\" in column <Stock Code> and \"1.23\" in column <Price>\n" +
			"Row including: \"Mark And Spencer\" in column <Stock Description> and \"4.57\" in column <Price>\n" +
			"Row including: \"GB\" in column [<4>]\n" +
			"Row including: \"US\" in column <Country>\n"));
	thrown.expectMessage(containsIgnoringWhitespace(
			"but:  table  had:\n" + 
			"in row 2:\n"));
	thrown.expectMessage(containsIgnoringWhitespace(
			"cell in column: [2] , headed <Stock Description>, expected to be \"Mark And Spencer\"\n" +
			" was:\n" + 
			"   'Mark[s] And Spencer'\n" +
			"instead of:\n" +
			"   'Mark[] And Spencer'\n"));
	thrown.expectMessage(containsIgnoringWhitespace(
			"cell in column: [3] , headed <Price>, expected to be \"4.57\"\n" +
			" was:\n" +
			"   '4.5[6]'\n" +
			"instead of:\n" +
			"   '4.5[7]'\n"));
	thrown.expectMessage(containsIgnoringWhitespace("Actual row:\n"
			+ "MKS.L| <!***Marks And Spencer***!>| <!***4.56***!>|GB"));
	
	WebElement table = t.find(only(tableElement().that(hasName("a_table"))));
		
	assertThat(table,isTableElementThat(hasHeaders("Stock Code","Stock Description","Price","Country")));
		
	//TODO - figure out if can get rid of needing dataCellWithTextfix IsTableCell.with to accept string matchers as well as cell matchers
	assertThat(table,isTableElementThat(hasDataRows(
			rowIncluding(valueInColumn("Stock Code","VOD.L"),valueInColumn("Price","1.23")),
			rowIncluding(valueInColumn("Stock Description","Mark And Spencer"),valueInColumn("Price","4.57")),
			rowIncluding(valueInColumn(4,"GB")),
			rowIncluding(valueInColumn("Country","US"))
			)));
	}
	
	
	@Test public void
	assertsCustomCellTypes(){
		WebElement table = t.find(only(tableElement().that(hasName("a_table_with_nontext_cells"))));
		
		assertThat(table,isTableElementThat(
							hasHeaders("Stock Code", EMPTY_HEADER, EMPTY_HEADER, unselectedCheckboxCell())));
		
		assertThat(table,isTableElementThat(hasDataRows(
				includesCells(valueInColumn("Stock Code","VOD.L"), valueInColumn(2,tradeIconCell())),
				
				rowConsistingOf(	"MKS.L", 
									tradeIconCell(), 
									researchIconCellForIsin("MRKSSPNCRISIN"), 
									selectedCheckboxCell()
								))));
	}
	
	@Test public void
    describesErrorInCustomCellTypes(){
        WebElement table = t.find(only(tableElement().that(hasName("a_table_with_nontext_cells"))));
        thrown.expectMessage(containsIgnoringWhitespace(
                "Expected:  a table containing data rows:\n" +
				"\"MKS.L\",  " +
				"contains : {a link that: " +
				        "{has title \"Trade\" and " +
				        "has href a string ending with \"#\" " +
				        "and  contains : {a image that has src a string ending with \"icon_trade.gif\"}}},  " +
				"contains : {a link that: " +
				     "{has title \"Research\" and " +
				     "has href a string ending with \"research?isin=AMRKSSPNCRISIN\" " +
				     "and  contains : {a image that has src a string ending with \"icon_research.gif\"}}}," +
				"CheckboxCell:SELECTED"));
        thrown.expectMessage(containsIgnoringWhitespace(
                "but:  table  had:\n" +
				"in row 2:\n"+
				"cell in column: [3] ,  (with blank header), " +
				"expected to be   " +
				"contains : {a link that: " +
				               "{has title \"Research\" and " +
				               "has href a string ending with \"research?isin=AMRKSSPNCRISIN\" " +
				               "and  contains : {a image that has src a string ending with \"icon_research.gif\"}}}" +
				"child elements did not match:\n" +
				"Not found - but searching for link that: "+ 
							   "{has title \"Research\" and " +
							   "has href a string ending with \"research?isin=AMRKSSPNCRISIN\"} " +
				"<a> (id:link71)  - " +
				"not matched because href was ")); //can't really include file path here - consider only outputting end bit
        
        assertThat(table,isTableElementThat(hasDataRowsIncluding(
                rowConsistingOf("MKS.L", 
                                    tradeIconCell(), 
                                    researchIconCellForIsin("AMRKSSPNCRISIN"), 
                                    selectedCheckboxCell()
                                ))));
        
    }

	private static Matcher<TableCell> unselectedCheckboxCell() {
		return describedAs("CheckboxCell:UNSELECTED",cellContaining(checkbox().that(isUnselected())));
	}			
	
	private static Matcher<TableCell> selectedCheckboxCell() {
		return describedAs("CheckboxCell:SELECTED",cellContaining(checkbox().that(isSelected())));
	}		
	

	private Matcher<TableCell> researchIconCellForIsin(String isin) {
		return cellContaining(link()
								.that(hasTitle(equalTo("Research")))
								.that(hasHref(endsWith("research?isin=" + isin)))
								.that(hasSubElement(
										image().that(hasSrc(endsWith("icon_research.gif"))))));
	}



	private static Matcher<TableCell> tradeIconCell() {
		return cellContaining(link()
								.that(hasTitle(equalTo("Trade")))
								.that(hasHref(endsWith("#")))
								.that(hasSubElement(
										image().that(hasSrc(endsWith("icon_trade.gif"))))));
	}



	private static Matcher<WebElement> hasSrc(Matcher<String> srcMatcher) {
		return hasAttribute("src","src", srcMatcher);
	}

	private static Matcher<WebElement> hasTitle(Matcher<String> titleMatcher) {
		return hasAttribute("title", "title", titleMatcher);
	}

	private static Matcher<WebElement> hasHref(Matcher<String> stringMatcher) {
		return hasAttribute("href", "href", stringMatcher);
	}

	@Test public void
	complainsOfUnCheckedCheckbox(){
		
		thrown.expectMessage(containsIgnoringWhitespace(
				"Expected:  a table containing data rows:\n"
				+ "Row including: \"VOD.L\" in column <Stock Code> and CheckboxCell:SELECTED in column [<4>]\n"));
		
		thrown.expectMessage(containsIgnoringWhitespace(
				"in row 1:\n"
				+"      cell in column: [4] ,  (with blank header), expected to be CheckboxCell:SELECTED\n"
				+"child elements did not match:\n"
				+"Not found - \n"
				+"<input> (name:selectItemBox1)  - not matched because element was <unselected>\n"));
		
		WebElement table = t.find(only(tableElement().that(hasName("a_table_with_nontext_cells"))));
		
		assertThat(table,isTableElementThat(hasDataRowsIncluding(
				rowIncluding(valueInColumn("Stock Code","VOD.L"), valueInColumn(4, selectedCheckboxCell()  )  )
				)));
				
	}
	
	@Test
	public void 
	parsesLargeTableInReasonableTime(){
		thrown.expect(AssertionError.class);
		WebElement table = t.find(only(tableElement().that(hasName("a_large_table_with_11_cols_34_rows"))));
		long timeBefore = System.currentTimeMillis();
		
			
		//TODO - figure out if can get rid of needing dataCellWithTextfix IsTableCell.with to accept string matchers as well as cell matchers
		assertThat(table,isTableElementThat(hasDataRows(
				includesCells(valueInColumn("Stock Code",cellWithText(startsWith("VOD.L"))),         valueInColumn("Price","1.23")),
				includesCells(valueInColumn("Stock Description",cellWithText(startsWith("Marks"))),  valueInColumn("Price","4.56")),
				includesCells(valueInColumn(4,"GB")),
				includesCells(valueInColumn("Country","US")))));
		int timeTakenSecs = (int) ((System.currentTimeMillis()-timeBefore)/1000l);
		System.out.println("completed in " + timeTakenSecs + " seconds");
		assertThat("completes in less than 10 seconds",timeTakenSecs,OrderingComparison.lessThan(10));
		
	}

	@Test
	public void
	assertsTableIncludesRows(){
			
		WebElement table = t.find(only(tableElement().that(hasName("a_table"))));
					
		assertThat(table,isTableElementThat(hasDataRowsIncluding(
				includesCells(valueInColumn("Stock Code","VOD.L"),valueInColumn("Stock Description","Vodafone")))));

	}
	
	@Test
	public void 
	findsRowByValueInAColumn(){
		t.assertThatThe(
				only (elementOf(
						cell().that(
								isInColumnThat(isColumnIdentifiedBy("Price")))
							  .withinA(row().that(includesCells(valueInColumn("Stock Code", "MKS.L"))))
						      .withinA(table(tableElement().that(hasName("a_table")))))), 
			    hasText("4.56"));
	}
	
	@Test
	public void 
	describesFinderOfRowByValueInAColumn(){
		
		assertHasDescription(cell().that(
								isInColumnThat(isColumnIdentifiedBy("Price")))
							  .withinA(row().that(includesCells(valueInColumn("Stock Code", "MKS.L"))))
						      .withinA(table(tableElement().that(hasName("a_table")))), 
							"cell that is in column <Price> within:\n"+
						    "	a data row that includes cells: \"MKS.L\" in column <Stock Code>, " +
						    "within a table object formed from table that has name \"a_table\"");
	}
	
	@Test
	public void
	assertsTableRowUsingTableFinders(){
	
		thrown.expectMessage(containsIgnoringWhitespace("Expected: a data row that includes cells: \"MAKS.L\" in column <Stock Code>" +
				", within a table object formed from table that has name \"a_table\""));
		
		thrown.expectMessage(containsIgnoringWhitespace("within: Table: \n" +
				"Stock Code|Stock Description|Price|Country\n" + 
				"------------------------------------------\n" +
				"VOD.L|Vodafone|1.23|GB\n" +
				"MKS.L|Marks And Spencer|4.56|GB"));
		thrown.expectMessage(containsIgnoringWhitespace("Not found - nearest matches:\n" +
									"VOD.L|Vodafone|1.23|GB - not matched because \n" +
									"      cell in column: [1] , headed <Stock Code>, expected to be \"MAKS.L\"\n" +
									" was:\n" +
									"   '[VOD].L'\n" +
									"instead of:\n" +
									"   '[MAKS].L'"));
		t.assertPresenceOf(row().that(includesCells(valueInColumn("Stock Code", "MAKS.L"))).withinA(table(tableElement().that(hasName("a_table")))));

		
	}
	
	@Test
	public void
	assertsTableRowUsingTableMatchers(){
		WebElement table = t.find(only(tableElement().that(hasName("a_table"))));
		
		MatcherAssert.assertThat(table,WebDriverMatchers.isTableElementThat(
				WebDriverMatchers.hasDataRowsIncluding(
						WebDriverMatchers.rowIncluding(WebDriverMatchers.valueInColumn("Stock Code","MKS.L")))));
	}
	
}
