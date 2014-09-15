package jhc.redsniff.webdriver.transformers;

import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

import jhc.redsniff.internal.core.Transformer;

public class AttributeTransformer extends Transformer<WebElement, String>{

    private final String attribute;
    
    public AttributeTransformer(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("attribute '" + attribute+"'");
    }

    @Override
    public String transform(WebElement element, Description couldNotTransformDescription) {
        String downloadLocation = element.getAttribute(attribute);
        if (downloadLocation==null||downloadLocation.trim().equals("")) {
            couldNotTransformDescription.appendText( "no attribute '" + attribute + "'");
        }
        return downloadLocation;
    }
    
}
