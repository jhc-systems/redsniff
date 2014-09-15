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
package jhc.redsniff.internal.expectations;

import static jhc.redsniff.core.Describer.describable;
import static jhc.redsniff.core.Describer.newDescription;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class ContextPrintingExpectation<E, Q extends Quantity<E>, C> implements FindingExpectation<E, Q, C> {

    private final FindingExpectation<E, Q, C> wrappedExpectation;
    private SelfDescribing contextDescriber;
    
    public ContextPrintingExpectation(SelfDescribing contextDescriber, FindingExpectation<E, Q, C> wrappedExpectation) {
        this.contextDescriber = contextDescriber;
        this.wrappedExpectation = wrappedExpectation;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(contextDescriber).appendText(", ");
        wrappedExpectation.describeTo(description);
    }

    @Override
    public ExpectationCheckResult<E, Q> checkFrom(C context) {
        ExpectationCheckResult<E, Q> unadornedResult = wrappedExpectation.checkFrom(context);
        return adornedWithContext(context, unadornedResult);
    }

    @Override
    public ExpectationCheckResult<E, Q> checkFromFailFast(C context) {
    	 ExpectationCheckResult<E, Q> unadornedResult = wrappedExpectation.checkFromFailFast(context);
    	 return adornedWithContext(context, unadornedResult);
    }
	private ExpectationCheckResult<E, Q> adornedWithContext(C context,
			ExpectationCheckResult<E, Q> unadornedResult) {
		if(unadornedResult.meetsExpectation())
            return unadornedResult;
        else {
            Description adornedDescription = newDescription()
                    .appendText("within {")
                    .appendDescriptionOf(describable(context))
                    .appendText("\n}\n:")
                    .appendDescriptionOf(unadornedResult);
            return ExpectationCheckResult.unsatisfactoryResult(unadornedResult.foundQuantity(), adornedDescription);
        }
	}

}
