package jhc.redsniff.webdriver.table.matchers;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.MatcherUtil;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class IsTableWithNumberOfRows extends CheckAndDiagnoseTogetherMatcher<Table> {

    Matcher<Integer> numberOfRowsMatcher;
     
    public IsTableWithNumberOfRows(Matcher<Integer> numberOfRowsMatcher) {
        this.numberOfRowsMatcher = numberOfRowsMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" table with ").appendDescriptionOf(numberOfRowsMatcher).appendText(" rows");
    }

    @Override
    protected boolean matchesSafely(Table item, Description mismatchDescription) {
       return  MatcherUtil.matchAndDiagnose(numberOfRowsMatcher, item.dataRows().size(), mismatchDescription);
    }

    @Factory
    public static Matcher<Table> hasNumberOfDataRows(Matcher<Integer> numberOfRowsMatcher){
        return new IsTableWithNumberOfRows(numberOfRowsMatcher);
    }
    
    @Factory
    public static Matcher<Table> hasNumberOfDataRows(int n){
        return new IsTableWithNumberOfRows(Matchers.equalTo(n));
    }
}
