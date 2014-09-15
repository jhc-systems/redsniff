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
