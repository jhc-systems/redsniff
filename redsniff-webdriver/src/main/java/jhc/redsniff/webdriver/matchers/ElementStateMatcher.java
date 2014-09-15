package jhc.redsniff.webdriver.matchers;

import jhc.redsniff.internal.matchers.FeatureMatcher;

import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

//TODO - a bit pointless - it just adds that it's a WebElement Feature matcher
public abstract class ElementStateMatcher<T> extends FeatureMatcher<WebElement, T> {

    public ElementStateMatcher(Matcher<? super T> subMatcher, String featureDescription, String featureName) {
        super(subMatcher, featureDescription, featureName);
    }

    @Override
    protected T featureValueOf(WebElement actual) {
        return stateOf(actual);
    }

    protected abstract T stateOf(WebElement actual);
}
