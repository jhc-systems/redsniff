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