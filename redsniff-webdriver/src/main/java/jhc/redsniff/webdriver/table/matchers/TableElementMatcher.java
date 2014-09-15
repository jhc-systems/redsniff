package jhc.redsniff.webdriver.table.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class TableElementMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private Matcher<Table> tableMatcher;

    TableElementMatcher(Matcher<Table> tableMatcher) {
        this.tableMatcher = tableMatcher;
    }

    @Override
    protected boolean matchesSafely(WebElement element, Description mismatchDescription) {
        return matchAndDiagnose(tableMatcher, Table.fromTableElement(element), mismatchDescription, " table ");
    }

    public void describeTo(Description description) {
        description.appendText(" a table ");
        description.appendDescriptionOf(tableMatcher);
    }

    @Factory
    public static Matcher<WebElement> isTableElementThat(Matcher<Table> tableMatcher) {
        return new TableElementMatcher(tableMatcher);
    }
}