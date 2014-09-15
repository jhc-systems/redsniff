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
package jhc.redsniff.wicket.expectations;

import static jhc.redsniff.pageError.NoPageErrorExpectation.noPageErrorExpected;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.wicket.WicketPageExceptionTraceChecker;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class WithPageCheckExpectation{

	public static FindingExpectation<WebElement, CollectionOf<WebElement>, SearchContext>  pageCheckExpectation(){
		return noPageErrorExpected(new WicketPageExceptionTraceChecker());
	}
}
