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

import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.BaseMFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * Note this finds a *String* not a WebElement, since there is no way to get a valid &lt;title&gt; WebElement except in HtmlUnit
 */
public class TitleFinder extends BaseMFinder<String, SearchContext>{

    @Override
    public void describeTo(Description description) {
      description.appendText("title");
        
    }
    
    @Factory
    public static MFinder<String,SearchContext> title(){
        return new TitleFinder();
    }
    
    @Factory
    public static MFinder<String,SearchContext> title(String titleText){
        return new TitleFinder().that(isString(titleText));
    }

	@Override
	public CollectionOf<String> findFrom(SearchContext context,
			Description notFoundDescription) {
		 if(context instanceof WebDriver){
	            String title = ((WebDriver) context).getTitle();
	            return CollectionOf.collectionOf(title);
	        }
	        else {
	        	notFoundDescription.appendText("Cannot get title from inner element : "+Describer.describable(((WebElement)context)).toString());
	        	return CollectionOf.empty();
	        }
	}

	@Override
	public MFinder<String, SearchContext> asOptimizedFinder() {
		return this;
	}
}
