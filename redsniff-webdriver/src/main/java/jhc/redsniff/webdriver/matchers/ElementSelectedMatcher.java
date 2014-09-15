package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.webdriver.matchers.ElementSelectedMatcher.ElementSelectedState.SELECTED;
import static jhc.redsniff.webdriver.matchers.ElementSelectedMatcher.ElementSelectedState.UNSELECTED;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;


public class ElementSelectedMatcher extends ElementStateMatcher<ElementSelectedMatcher.ElementSelectedState> {

    protected static enum ElementSelectedState{
        SELECTED, UNSELECTED;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
	
	public ElementSelectedMatcher(ElementSelectedState expectedSelectionState) {
	    super(is(expectedSelectionState), "", "element");
    }
	
    @Override
    protected ElementSelectedState stateOf(WebElement element) {
		return element.isSelected()?SELECTED:UNSELECTED;
	}

    @Factory
	public static Matcher<WebElement> isSelected(){
		return new ElementSelectedMatcher(SELECTED);
	}
	
	@Factory
	public static Matcher<WebElement> isUnselected(){
	    return new ElementSelectedMatcher(UNSELECTED);
	}
	
	@Factory
	public static Matcher<WebElement> hasSelectedState(boolean selected){
	    return new ElementSelectedMatcher(selected?SELECTED:UNSELECTED);
	}
	

}
