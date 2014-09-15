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

import jhc.redsniff.core.Describer;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class ExpectationCheckResult<E, Q extends Quantity<E>> implements SelfDescribing {
    private final boolean meetsExpectation;
    private final Q foundElements;
    private final Description dissatisfactionDescription;

    public ExpectationCheckResult(boolean satisfied, Q foundElements, Description diagnosticDescription) {
        this.meetsExpectation = satisfied;
        this.foundElements = foundElements;
        this.dissatisfactionDescription = diagnosticDescription;
    }

    public ExpectationCheckResult(boolean satisfactory, Q foundElements) {
        this(satisfactory, foundElements, new Description.NullDescription());
    }

    public boolean meetsExpectation() {
        return meetsExpectation;
    }

    public Q foundQuantity() {
        return foundElements;
    }

    @Override
    public void describeTo(Description description) {
        if (!meetsExpectation)
            description.appendText(dissatisfactionDescription.toString());
        else
            description.appendText("Found elements (could add list of elements found here?)");// TODO
    }

    public static <E, Q extends Quantity<E>> ExpectationCheckResult<E, Q> unsatisfactoryResult(
            Q foundElements, Description dissatisfactionDescription) {
        return new ExpectationCheckResult<E, Q>(false, foundElements, dissatisfactionDescription);
    }

    public static <E, Q extends Quantity<E>> ExpectationCheckResult<E, Q> satisfactoryResult(Q foundElements) {
        return new ExpectationCheckResult<E, Q>(true, foundElements);
    }
    
    public String toString(){
    	return Describer.newDescription().appendDescriptionOf(this).toString();
    }
}