package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.webdriver.matchers.EnabledElementMatcher.ElementEnabledState.DISABLED;
import static jhc.redsniff.webdriver.matchers.EnabledElementMatcher.ElementEnabledState.ENABLED;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class EnabledElementMatcher extends ElementStateMatcher<EnabledElementMatcher.ElementEnabledState> {

    protected static enum ElementEnabledState {
        ENABLED, DISABLED;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public EnabledElementMatcher(ElementEnabledState expectedEnablementState) {
        super(is(expectedEnablementState), "", "element");
    }

    @Override
    protected ElementEnabledState stateOf(WebElement item) {
        return item.isEnabled() ? ENABLED : DISABLED;
    }

    @Factory
    public static Matcher<WebElement> isEnabled() {
        return new EnabledElementMatcher(ENABLED);
    }

    @Factory
    public static Matcher<WebElement> isDisabled() {
        return new EnabledElementMatcher(DISABLED);
    }

}
