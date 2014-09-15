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
import org.openqa.selenium.WebElement;

public class WebElementDescribaliser implements Describaliser<WebElement>{

	@Override
	public SelfDescribing describable(final WebElement element) {
		
		return new SelfDescribing() {
					@Override
					public void describeTo(Description description) {
		    				describeElementTo(element,description);
					}
		
					private  void describeElementTo(WebElement element,Description description) {
						description
						.appendText("<"+element.getTagName() +">");
						
						describeElementAttribute("class",element,description);
						describeElementAttribute("name",element,description);
						describeElementAttribute("id",element,description);
						
						//TODO -:
						describeTextOfElement(element, description);
						describeChildElements(element, description);
					}
					
					//TODO - this is too costly at the moment to be worth it
					private  void describeTextOfElement(WebElement element,
							Description description) {
						String elementText = element.getText();
						description		
							.appendText(" " +(elementText==null?"":elementText));
					}
					
					private void describeElementAttribute(String attribute,WebElement element, Description description) {
					    	String attributeValue = element.getAttribute(attribute);
					    	if(attributeValue!=null && !attributeValue.isEmpty()) 
					    		description.appendText(" (" + attribute+":" + attributeValue + ")");
					}
					
					private  void describeChildElements(WebElement element,
								Description description) {
							//TODO - would be nice to output child elements - doesn't work not sure why
							//List<WebElement> childElements = element.findElements(By.xpath("*"));
							//childElements.size();
							//
							//if(!childElements.isEmpty()){
		//						description.appendText("\n\t<<");
		//						for(WebElement child:childElements)
		//							describeElementTo(child,description);
		//						description.appendText(">>\n");
							//}
					}
				};
	}
}