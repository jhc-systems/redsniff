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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import jhc.redsniff.core.Locator;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.finders.LocatorFinder;
import jhc.redsniff.webdriver.finders.ByFinder;
import jhc.redsniff.webdriver.matchers.CssClassNameMatcher;
import jhc.redsniff.webdriver.matchers.IdMatcher;
import jhc.redsniff.webdriver.matchers.NameMatcher;
import jhc.redsniff.webdriver.matchers.TagNameMatcher;
import static jhc.redsniff.core.Describer.newDescription;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


public class ByFinderTest {

    @Test public void
    shouldCreateByIdLocatorForById(){
        Locator<WebElement, SearchContext> constructedLocator = getLocatorConstructedFor(By.id("123"));
        assertThat(constructedLocator,instanceOf(IdMatcher.class));
        assertThat(locatorTextOf(constructedLocator), is("element with id \"123\""));
    }
    
    @Test public void
    shouldCreateByNameLocatorForByName(){
        Locator<WebElement, SearchContext> constructedLocator = getLocatorConstructedFor(By.name("theName"));
        assertThat(constructedLocator,instanceOf(NameMatcher.class));
        assertThat(locatorTextOf(constructedLocator), is("element with name \"theName\""));
    }
    
    @Test public void
    shouldCreateByClassNameLocatorForByClassName(){
        Locator<WebElement, SearchContext> constructedLocator = getLocatorConstructedFor(By.className("theClassName"));
        assertThat(constructedLocator,instanceOf(CssClassNameMatcher.class));
        assertThat(locatorTextOf(constructedLocator), is("element with cssClass \"theClassName\""));
    }
    
    @Test public void
    shouldCreateByTagNameLocatorForByTagName(){
        Locator<WebElement, SearchContext> constructedLocator = getLocatorConstructedFor(By.tagName("tag"));
        assertThat(constructedLocator,instanceOf(TagNameMatcher.class));
        assertThat(locatorTextOf(constructedLocator), is("tag"));
    }
    
    @Test public void
    shouldCreateBasicWrappedLocatorForByXpath(){
        Locator<WebElement, SearchContext> constructedLocator = getLocatorConstructedFor(By.xpath("//div"));
        assertThat(constructedLocator, not(instanceOf(Matcher.class)));
        assertThat(locatorTextOf(constructedLocator), is("found By.xpath: //div"));
    }
    
    

    private String locatorTextOf(Locator<WebElement, SearchContext> constructedLocator) {
        Description description = newDescription();
        constructedLocator.describeLocatorTo(description);
        String string = description.toString();
        return string;
    }

    private Locator<WebElement, SearchContext> getLocatorConstructedFor(By by) {
        MFinder<WebElement, SearchContext> byFinder = ByFinder.foundBy(by);
        assertThat(byFinder, instanceOf(LocatorFinder.class));
        LocatorFinder<WebElement, SearchContext> locatorFinder = (LocatorFinder<WebElement, SearchContext>)byFinder;
        Locator<WebElement, SearchContext> rootLocator = locatorFinder.locator();
        return rootLocator;
    }
    
    
}
