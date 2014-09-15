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
package jhc.redsniff.webdriver.table.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.webdriver.table.matchers.IsTableRowConsistingOf.rowConsistingOf;

import java.util.List;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsTableWithHeader extends CheckAndDiagnoseTogetherMatcher<Table> {
	private final Matcher<TableRow> headerRowMatcher;

	public IsTableWithHeader(Matcher<TableRow> headerMatcher){
		this.headerRowMatcher = headerMatcher;
	}


	public void describeTo(Description description) {
		description.appendText("with header:").appendDescriptionOf(headerRowMatcher);
	}

	@Override
	protected boolean matchesSafely(Table table, Description mismatchDescription) {
	    return matchAndDiagnose(headerRowMatcher, table.headerRow(), mismatchDescription, "had header ");
	}

	@Factory 
	public  static Matcher<Table> hasHeaderRow(Matcher<TableRow> headerMatcher){
		return new IsTableWithHeader(headerMatcher);
	}

	@Factory
	public static Matcher<Table> hasHeader(List<Matcher<TableCell>> headerRowCells){
		return hasHeaderRow(rowConsistingOf(headerRowCells));
	}
	
	@Factory
	public static Matcher<Table> hasHeaders(Object... headers ){
		return hasHeaderRow(rowConsistingOf(headers));
	}
}
