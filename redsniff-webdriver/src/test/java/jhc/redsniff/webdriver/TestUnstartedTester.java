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
package jhc.redsniff.webdriver;

import static jhc.redsniff.webdriver.Finders.div;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;
import jhc.redsniff.webdriver.TestShellImplementations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestUnstartedTester {
    @Rule
    public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();
    
    @Test
    public void throwsAssertionErrorForAnyAction(){
        thrown.expectMessage("Test not yet started!");
        RedsniffWebDriverTester tester = new TestShellImplementations().getTester();
        tester.find(div());
    }
    
    @Test
    public void throwsAssertionErrorForAssertion(){
        thrown.expectMessage("Test not yet started!");
        RedsniffWebDriverTester tester = new TestShellImplementations().getTester();
        tester.assertPresenceOf(div());
    }
    
    @Test
    public void throwsAssertionErrorForClick(){
        thrown.expectMessage("Test not yet started!");
        RedsniffWebDriverTester tester = new TestShellImplementations().getTester();
        tester.clickOn(div());
    }
}
