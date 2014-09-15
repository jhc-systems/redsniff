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
package jhc.redsniff.webdriver.describe;


import jhc.redsniff.internal.describe.Describaliser;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverDescribaliser implements Describaliser<WebDriver>{

	@Override
	public SelfDescribing describable(final WebDriver driver) {
		return new SelfDescribing() {
		
			@Override
			public void describeTo(Description description) {
				new WebElementDescribaliser().describable(rootElementOfPage(driver)).describeTo(description);
			}

			private WebElement rootElementOfPage(final WebDriver driver) {
				return driver.findElement(By.tagName("html"));
			}
		};
	}
}