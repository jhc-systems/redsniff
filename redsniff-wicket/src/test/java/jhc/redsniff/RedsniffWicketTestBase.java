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
package jhc.redsniff;

import static jhc.redsniff.webdriver.WebDriverMatchers.isString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;
import jhc.redsniff.webdriver.TestShellImplementations;
import jhc.redsniff.webdriver.factory.WebDriverFactory;
import jhc.redsniff.webdriver.junit.RedsniffWebDriverJUnitRunner;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(RedsniffWebDriverJUnitRunner.class)
public abstract class RedsniffWicketTestBase {

	@Rule
	public ExpectedException thrown = ExpectedException.none()
														.handleAssertionErrors();

	
	protected RedsniffWebDriverTester t=new TestShellImplementations().getTester();
	
	
	
	public RedsniffWicketTestBase() {
		super();
	}

	@Before
	public void setup() {
	    t = new RedsniffWebDriverTester(getWebDriver());
		gotoTestPage();
	}

	protected void gotoTestPage() {
		t.goTo(getTestUrl());
	}

    protected WebDriver getWebDriver() {
        return WebDriverFactory.getWebDriver();
    }
	
	public String ultimateClassName(){
	    return getClass().getName();
	}

	private String getTestUrl() {
        String pageFileName = getTestPage();
		String fileUrl;
		
		if(pageFileName.contains(":"))
			
			if(pageFileName.startsWith("file:///")||pageFileName.startsWith("http://"))
					fileUrl=pageFileName;
			else
				fileUrl=fileUrlFromWindowsPath(pageFileName);
		else
			fileUrl=fileUrlFromWindowsPath(currentWorkingDirectory() +"\\src\\test\\resources\\"+ pageFileName);
        return fileUrl;
    }

	private String currentWorkingDirectory() {
		return System.getProperty("user.dir");
	}
//TODO move somewhere common
	private static String fileUrlFromWindowsPath(String pageFileName) {
		return "file:///" + pageFileName.replaceAll("\\\\","/");
	}

	public  String getTestPage(){
	    return getClass().getSimpleName() + ".html";
	}


	  protected <E,Q extends Quantity<E>, C> void assertHasDescription(
			Finder<E, Q, C> finder, String expected) {
				Description description = Describer.newDescription();
				finder.describeTo(description);
				assertThat(description.toString(), isString(expected));
			}

	protected void expectError(String string) {
		thrown.expectMessage(containsIgnoringWhitespace(string));
	}

	public static Matcher<String> containsIgnoringWhitespace(final String expected){
    	return new TypeSafeMatcher<String>(){
    
    		@Override
    		public void describeTo(Description description) {
    			description.appendText(expected); //don't bother to add the fact that it contains ignoring case
    			
    		}
    
    		@Override
    		protected boolean matchesSafely(String actual) {
    			String strippedActual = stripSpace(actual);
    			String strippedExpected =  stripSpace(expected);
    			return containsString(strippedExpected).matches(strippedActual); 
    		}
    
    		private String stripSpace(String string) {
    			return new IsEqualIgnoringWhiteSpace("").stripSpace(string);
    		}
    	};
    		
    }

}