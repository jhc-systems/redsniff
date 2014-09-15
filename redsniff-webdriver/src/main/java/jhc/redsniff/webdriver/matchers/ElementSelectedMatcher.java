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
