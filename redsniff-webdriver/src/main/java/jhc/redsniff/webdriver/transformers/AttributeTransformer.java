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
