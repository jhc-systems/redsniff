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

import static jhc.redsniff.internal.matchers.HasSubElement.hasSubElement;
import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class IsTableCellContaining extends CheckAndDiagnoseTogetherMatcher<TableCell>{

	private final Matcher<SearchContext> elementMatcher;
	
	public IsTableCellContaining(Matcher<SearchContext> elementMatcher) {
		super();
		this.elementMatcher = elementMatcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(" ")
					.appendDescriptionOf(elementMatcher);	
	}

	@Override
	protected boolean matchesSafely(TableCell actualCell,	Description mismatchDescription) {
		return matchAndDiagnose(elementMatcher, actualCell.element(), mismatchDescription);
	}

	public static Matcher<TableCell> cellContaining(MFinder<WebElement, SearchContext> childFinder) {
		return new IsTableCellContaining(hasSubElement(childFinder));
	}

}
