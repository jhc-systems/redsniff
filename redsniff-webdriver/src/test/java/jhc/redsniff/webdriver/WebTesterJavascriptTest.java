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

import static jhc.redsniff.core.FindingExpectations.expectationOfMatching;
import static jhc.redsniff.internal.finders.OnlyFinder.only;
import static jhc.redsniff.webdriver.Finders.textbox;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasId;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasValue;
import static jhc.redsniff.webdriver.Sugar.into;
import jhc.redsniff.RedsniffTestBase;
import jhc.redsniff.core.MFinder;

import org.junit.Test;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class WebTesterJavascriptTest extends RedsniffTestBase {
    
    @Test
    public void testOnBlur(){
        MFinder<WebElement, SearchContext> textbox = textbox().that(hasId("fname"));
        t.type("hello", into(textbox));
        t.tab(textbox);
        t.waitFor(expectationOfMatching( only(textbox), hasValue("HELLO")));
    }
    
//   @Test
//   public void testGmail(){
//       t.goTo("http://www.gmail.com");
//       t.type("nic.infante@gmail.com", into(inputFinderForType("email")));
//       t.type("MyPassword", into(passwordField()));
//       t.clickOn(button("Sign in"));
//       t.waitFor(presenceOf(link().that(hasText(startsWith("Inbox")))));
//       WebElement emailRow = t.find(first(tag("tr").that(hasSubElement(span().that(hasSimpleAttribute("email", "brenda.granfield@gmail.com"))))));
//       t.from(emailRow)
//           .tick(div().that(hasCssClass("T-Jo-auh")));
//       
//   }
    
}
