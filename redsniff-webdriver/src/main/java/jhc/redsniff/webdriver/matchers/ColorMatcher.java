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
package jhc.redsniff.webdriver.matchers;

import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;

public class ColorMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private final Color expectedColor;
    private final String attributeName;

    protected ColorMatcher(String attributeName, Color expectedColor) {
        this.expectedColor = expectedColor;
        this.attributeName = attributeName;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("rendered with " + attributeName + " " + toString(expectedColor));
    }

    private String toString(Color color) {
        for (Colors namedColor : Colors.values()) {
            if (namedColor.getColorValue().equals(color))
                return namedColor.toString().toLowerCase();
        }
        return color.asHex().toLowerCase();
    }

    @Override
    protected boolean matchesSafely(WebElement item,
            Description mismatchDescription) {
        Color actualColor = Color.fromString(item.getCssValue(attributeName));
        if (actualColor.equals(expectedColor))
            return true;
        else {
            mismatchDescription.appendText(attributeName + " was " + toString(actualColor));
            return false;
        }
    }

    public static Matcher<WebElement> hasColor(String colorAsString) {
        return new ColorMatcher("color", Color.fromString(colorAsString));
    }

    public static Matcher<WebElement> hasBackgroundColor(String colorAsString) {
        return new ColorMatcher("background-color", Color.fromString(colorAsString));
    }
}
