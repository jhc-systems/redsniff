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
