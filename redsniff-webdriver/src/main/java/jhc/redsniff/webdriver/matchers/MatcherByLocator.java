package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.locators.MatcherLocator;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.StringMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public abstract class MatcherByLocator extends CheckAndDiagnoseTogetherMatcher<WebElement>
        implements MatcherLocator<WebElement, SearchContext> {

    private final Matcher<WebElement> wrappedMatcher;
    private final String literalAttribute;

    public MatcherByLocator(String literalAttribute) {
        this(StringMatcher.isString(literalAttribute), literalAttribute);
    }

    public MatcherByLocator(Matcher<String> stringMatcher) {
        this(stringMatcher, null);
    }

    public MatcherByLocator(Matcher<String> stringMatcher, String literalAttribute) {
        this.wrappedMatcher = getWrappedMatcher(stringMatcher);
        this.literalAttribute = literalAttribute;
    }

    protected abstract By getByLocator(String literalAttribute);

    protected boolean usedLiteralArgument() {
        return literalAttribute != null;
    }

    public abstract Matcher<WebElement> getWrappedMatcher(Matcher<String> stringMatcher);

    @Override
    public CollectionOf<WebElement> findElementsFrom(SearchContext context) {
        return CollectionOf.collectionOf(getByLocator(literalAttribute).findElements(context));
    }

    @Override
    public boolean canBehaveAsLocator() {
        return usedLiteralArgument();
    }

    @Override
    public void describeTo(Description description) {
        wrappedMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(WebElement actual, Description mismatchDescription) {
        return matchAndDiagnose(wrappedMatcher, actual, mismatchDescription);
    }

    @Override
    public void describeLocatorTo(Description description) {
        description.appendText("element with " + nameOfAttributeUsed() + " ");
        if (usedLiteralArgument())
            description.appendText("\"" + literalAttribute + "\"");
        else
            wrappedMatcher.describeTo(description);
    }

}
