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
import static jhc.redsniff.webdriver.Finders.button;
import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.Finders.fourth;
import static jhc.redsniff.webdriver.Finders.image;
import static jhc.redsniff.webdriver.Finders.only;
import static jhc.redsniff.webdriver.Finders.textbox;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasCssClass;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

@Ignore
public class GoogleImageExample {

	
	
	@Test
	public void findsGoogleImage() throws Exception {
		RedsniffWebDriverTester t = new RedsniffWebDriverTester(new ChromeDriver());
		t.goTo("https://images.google.com/");
		t.type("Nic Infante", textbox().that(hasName("q")));
		t.clickOn(only(button()));
		t.waitFor(div().that(hasText("Images")));
		t.clickOn(fourth(image().that(hasCssClass("rg_i"))));
	}
}
