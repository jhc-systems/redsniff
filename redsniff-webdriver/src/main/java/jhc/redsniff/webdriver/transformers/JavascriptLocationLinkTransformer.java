package jhc.redsniff.webdriver.transformers;

import jhc.redsniff.internal.core.Transformer;

import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class JavascriptLocationLinkTransformer extends Transformer<WebElement, String>{

    private final String attribute;
    
    public JavascriptLocationLinkTransformer(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" link from javascript redirect in '"+attribute+"'");
    }

    @Override
    public String transform(WebElement element, Description couldNotTransformDescription) {
        String attContents = element.getAttribute(attribute);
        if (attContents==null||attContents.trim().equals("")) {
            couldNotTransformDescription.appendText( "no link from attribute '" + attribute + "'");
            return null;
        }
        String pattern= "^.*?window\\.location\\.href\\=\\'(.*?)\\'.*?$";
        if(!attContents.matches(pattern)){
            couldNotTransformDescription.appendText( "no redirect found in attribute '" +"' -  contents were: " + attContents);
            return null;
        }
        return attContents.replaceAll("^.*?window\\.location\\.href\\=\\'(.*?)\\'.*?$", "$1");
    }
    
    
    public static Transformer<WebElement, String> byJavascriptRedirect(String attribute){
        return new JavascriptLocationLinkTransformer(attribute);
    }
    

    public static Transformer<WebElement, String> byJavascriptOnClick(){
        return new JavascriptLocationLinkTransformer("onclick");
    }
}
