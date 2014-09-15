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
import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasId;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.matchers.TagNameMatcher.hasTagName;
import static org.junit.Assert.*;
import jhc.redsniff.RedsniffTestBase;
import jhc.redsniff.core.MFinder;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class WebTesterPerformanceTest extends RedsniffTestBase {


	@Test
	@Ignore
	public void createTestData(){
		System.out.println("<html>\n\t<body>");
		for(int i=1;i<=1000;i++){
			int id=  (int)(1000000 * Math.random()) ;	
			System.out.println("\t\t<div() id='"+id+"'>Some text for "+id+"</div()>");
		}
		System.out.println("\t</body>\n</html>");
	}
	
	//TODO - actually need to make some assertions about relative timings not just print stuff out...
	//possibly also add timeout @Rule
	//@Ignore(" this really needs to check relative times not print them out")
	@Test public void
	getsByTagNameThenFiltersForId(){
		recordTimeFor(div().that(hasId("730447")));
		recordTimeFor(elementFound(By.id("730447")).that(hasTagName("div")));
		recordTimeFor(div().that(hasId("730447")));
		recordTimeFor(elementFound(By.id("730447")).that(hasTagName("div")));
		recordTimeFor(div().that(hasId("730447")));
		recordTimeFor(elementFound(By.id("730447")).that(hasTagName("div")));
		recordTimeFor(elementFound(By.id("730447")));
		recordTimeFor(elementFound(By.id("730447")).that(hasTagName("div")));
		recordTimeFor(elementFound(By.id("730447")));
		recordTimeFor(elementFound(By.id("730447")).that(hasTagName("div")));
		recordTimeFor(elementFound(By.id("730447")));
	}
	
	//TODO - actually need to make some assertions about relative timings not just print stuff out...
	//@Ignore(" this really needs to check relative times not print them out")
	@Test public void
	getsByTagNameThenFiltersForName(){
		recordTimeFor(div().that(hasName("730447")));
		recordTimeFor(elementFound(By.name("730447")));
		recordTimeFor(elementFound(By.xpath(".//*[@name = '730447']")));
	}
	
	
	

	private void recordTimeFor(MFinder<WebElement,SearchContext> finder) {
		long start = System.currentTimeMillis();
		for(int i=0;i<=10;i++){
			t.assertPresenceOf(finder);
		}
		long duration = System.currentTimeMillis() - start;
		System.out.println(newDescription()
							.appendDescriptionOf(finder)
							.appendText(" took " + duration + " msecs").toString());
	}
}
