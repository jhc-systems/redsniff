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
package jhc.redsniff.webdriver.finders;



import static jhc.redsniff.internal.core.CollectionOf.collectionOf;
import static jhc.redsniff.webdriver.matchers.CssClassNameMatcher.hasCssClass;
import static jhc.redsniff.webdriver.matchers.IdMatcher.hasId;
import static jhc.redsniff.webdriver.matchers.NameMatcher.hasName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.LocatorFinder;
import jhc.redsniff.webdriver.matchers.TagNameMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * TODO - this should construct a {@link LocatorFinder} appropriate for the By supplied
 * @author Nic
 *
 */
public class ByFinder extends LocatorFinder<WebElement, SearchContext> {


	public  ByFinder(By by){
	    super(locatorFor(by));
	}
	
	
	public static Locator<WebElement, SearchContext> locatorFor(final By by) {
	    String byAsString = by.toString();
	    Pattern pattern = 
	            Pattern.compile("^By\\.(\\w+)\\: (.*?)$");
	    Matcher regexMatcher = pattern.matcher(byAsString);
	   if(!regexMatcher.matches())
	       throw new IllegalArgumentException("didn't match By.xxx: <arg>");
	   
	   final String byType = regexMatcher.group(1);
	   final String byArg = regexMatcher.group(2);
	   
	   if(byType.equals("id"))
	       return hasId(byArg);
	   else if(byType.equals("name"))
	       return hasName(byArg);
	   else if(byType.equals("className"))
           return hasCssClass(byArg);
	   else if(byType.equals("tagName"))
           return TagNameMatcher.hasTagName(byArg);
	   else
	       return new Locator<WebElement, SearchContext>() {

            @Override
            public void describeLocatorTo(Description description) {
                description.appendText("found " + by.toString());
            }

            @Override
            public CollectionOf<WebElement> findElementsFrom(SearchContext context) {
               return collectionOf(by.findElements(context));
            }

            @Override
            public String nameOfAttributeUsed() {
                return byType;
            }
        };
    }



	@Factory
	public static MFinder<WebElement, SearchContext> foundBy(By by){
		return new ByFinder(by);
	}

}
