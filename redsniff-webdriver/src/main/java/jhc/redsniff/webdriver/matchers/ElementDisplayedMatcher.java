package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.webdriver.matchers.ElementDisplayedMatcher.ElementDisplayedState.DISPLAYED;
import static jhc.redsniff.webdriver.matchers.ElementDisplayedMatcher.ElementDisplayedState.HIDDEN;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class ElementDisplayedMatcher extends ElementStateMatcher<ElementDisplayedMatcher.ElementDisplayedState>{

    protected static enum ElementDisplayedState{
        DISPLAYED, HIDDEN;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
    
    public ElementDisplayedMatcher(ElementDisplayedState expectedSelectionState) {
        super(is(expectedSelectionState), "", "element");
    }
    
    @Override
    protected ElementDisplayedState stateOf(WebElement element) {
        return element.isDisplayed()?DISPLAYED:HIDDEN;
    }

    @Factory
    public static Matcher<WebElement> isDisplayed(){
        return new ElementDisplayedMatcher(DISPLAYED);
    }
    
    @Factory
    public static Matcher<WebElement> isHidden(){
        return new ElementDisplayedMatcher(HIDDEN);
    }
}
