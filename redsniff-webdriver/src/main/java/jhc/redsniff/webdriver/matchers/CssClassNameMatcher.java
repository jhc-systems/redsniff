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

import static jhc.redsniff.webdriver.WebDriverMatchers.isString;
import static jhc.redsniff.webdriver.matchers.SimpleAttributeMatcher.hasSimpleAttribute;
import static org.hamcrest.core.DescribedAs.describedAs;
import jhc.redsniff.internal.locators.MatcherLocator;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.util.StringDiffFormatter;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class CssClassNameMatcher extends MatcherByLocator {

	private static final int specificity = Specifities.specifityOf(CssClassNameMatcher.class);
    public CssClassNameMatcher(Matcher<String> cssClassNameMatcher) {
        super(wrapMatcher(cssClassNameMatcher));
    }

    public CssClassNameMatcher(String literalClassName) {
        super(wrapMatcher(describedAs("\""+literalClassName+"\"", containsWord(literalClassName))), literalClassName);
    }

    @Override
    public String nameOfAttributeUsed() {
        return "cssClass";
    }

    @Override
    public By getByLocator(String literalName) {
        return By.className(literalName);
    }

    private static Matcher<WebElement> wrapMatcher(Matcher<String> cssClassNameMatcher) {
        return hasSimpleAttribute("class", "css class", cssClassNameMatcher);
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasCssClass(String value) {
        return new CssClassNameMatcher(value);
    }

    @Factory
    public static Matcher<WebElement> hasCssClass(Matcher<String> cssClassNameMatcher) {
        return new CssClassNameMatcher(cssClassNameMatcher);
    }

    private static class ContainsWord extends CheckAndDiagnoseTogetherMatcher<String>{

    	private final String word;
    	
		public ContainsWord(String word) {
			this.word = word;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(word);
		}

		@Override
		protected boolean matchesSafely(String actual,	Description mismatchDescription) {
			
			String[] actualSplit = actual.split("\\s+");
			boolean matches = expectedWordContainedIn(actualSplit);
			if(!matches){
				if(actualSplit.length<=1){
//						 String diff = new ComparisonCompactor(20, word, actual)
//					        .compact(" was:\n   '%2$s'\ninstead of:\n   '%1$s'");
					
					String diff = new StringDiffFormatter().formatDifference(word, actual, " was:\n   '%1$s'\ninstead of:\n   '%2$s'");
						mismatchDescription.appendText(
		                    diff);
				}
				else 
					mismatchDescription.appendText("was '" + actual + "'");
			}
			return matches;
		}

		public boolean expectedWordContainedIn(String[] split) {
			Matcher<String> stringMatcher = isString(word);
			for(String actualWord:split)
				if(stringMatcher.matches(actualWord))
					return true;
			return false;
		}
    	
    }
    private static ContainsWord containsWord(String word){
    		return new ContainsWord(word);
    }

	@Override
	public int specifity() {
		return specificity;
	}
}
