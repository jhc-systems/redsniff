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
package jhc.redsniff.webdriver;

import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.Quantity;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class Sugar {

    /**
     * Syntactic sugar to use with {@link HamcrestWebDriverTestCase#type(String, Finder<WebElement,
     * SearchContext>)}, e.g. type("cheese", into(textbox())); The into() method simply returns its
     * argument.
     */
    public static <Q extends Quantity<WebElement>> Finder<WebElement,Q, SearchContext> into(Finder<WebElement,Q, SearchContext> input) {
    	return input;
    }

    /**
     * Syntactic sugar to use with {@link HamcrestWebDriverTestCase#type(String, Finder<WebElement,
     * SearchContext>)}, e.g. chooseOption("cheese", on(dropDown())); The on() method simply returns its
     * argument.
     */
    public static MFinder<WebElement, SearchContext> on(MFinder<WebElement, SearchContext> input) {
    	return input;
    }

}
