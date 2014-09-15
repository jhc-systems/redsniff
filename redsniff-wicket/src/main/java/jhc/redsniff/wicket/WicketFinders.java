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
package jhc.redsniff.wicket;

import static jhc.redsniff.wicket.ByWicketPath.byWicketPath;
import static jhc.redsniff.wicket.ByWicketPath.byWicketXPath;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.webdriver.finders.ByFinder;

import org.hamcrest.Factory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class WicketFinders {
    private WicketFinders() {}
	@Factory
	public static MFinder<WebElement, SearchContext>  wicketItemByWXPath(String wicketXPath) {
		return new ByFinder(byWicketXPath(wicketXPath));
	  }

	@Factory
    public static MFinder<WebElement, SearchContext>  wicketItemByPath(String wicketPath) {
        return new ByFinder(byWicketPath(wicketPath));
      }
	
	@Factory
	public static MFinder<WebElement, SearchContext> authorityNotGrantedMessage() {
		return new ByFinder(byWicketXPath("//w{notGranted}"));
	}

}
