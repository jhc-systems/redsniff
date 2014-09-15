package jhc.redsniff.webdriver;

import jhc.redsniff.internal.core.Transformer;
import jhc.redsniff.webdriver.transformers.AttributeTransformer;
import jhc.redsniff.webdriver.transformers.JavascriptLocationLinkTransformer;

import org.openqa.selenium.WebElement;

public final class Transformers {

    private Transformers(){}
    
    public static Transformer<WebElement, String> toAttribute(String attribute){
        return new AttributeTransformer(attribute);
    }
    
    public static Transformer<WebElement, String> toJavascriptLocationLink(String attribute){
        return new JavascriptLocationLinkTransformer(attribute);
    }
}
