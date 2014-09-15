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
package jhc.redsniff.internal.describedas;

import jhc.redsniff.core.Locator;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public class LocatorDescribedAs<E, C> implements Locator<E, C> {

    private String customDescriptionText;
    private Locator<E, C> locator;

    public LocatorDescribedAs(String customDescriptionText, Locator<E, C> locator) {
        this.customDescriptionText = customDescriptionText;
        this.locator = locator;
    }

    @Override
    public CollectionOf<E> findElementsFrom(C context) {
        return locator.findElementsFrom(context);
    }

    @Override
    public String nameOfAttributeUsed() {
        return locator.nameOfAttributeUsed();
    }

    @Override
    public void describeLocatorTo(Description description) {
        description.appendText(customDescriptionText);
    }
}