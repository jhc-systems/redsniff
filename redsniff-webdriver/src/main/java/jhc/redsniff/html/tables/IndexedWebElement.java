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
package jhc.redsniff.html.tables;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class IndexedWebElement  {
	private final int index;
	private final WebElement webElement;
	public IndexedWebElement(int index, WebElement webElement) {
		this.index = index;
		this.webElement = webElement;
	}
	public int index() {
		return index;
	}
	public WebElement webElement() {
		new IndexedWebElement(0,webElement);
		return webElement;
	}
	public static List<IndexedWebElement> indexedElements(List<WebElement> webElements){
		int i=0;
		List<IndexedWebElement> indexedList = new ArrayList<IndexedWebElement>();
		for(WebElement webElement:webElements)
			indexedList.add(new IndexedWebElement(i++, webElement));
		return indexedList;
	}
}
