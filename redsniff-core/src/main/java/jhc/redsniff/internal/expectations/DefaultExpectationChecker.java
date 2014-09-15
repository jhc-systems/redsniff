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

import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.internal.expectations.ExpectationCheckResult.unsatisfactoryResult;
import static jhc.redsniff.util.Util.failWith;

import java.util.List;

import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import com.google.common.collect.Lists;

public class DefaultExpectationChecker<EX, QX extends Quantity<EX>, C> extends ExpectationChecker<C> {

    private final List<FindingExpectation<EX,QX, C>> whenNotMetChecks=Lists.<FindingExpectation<EX,QX, C>> newArrayList();
    
    private final List<FindingExpectation<EX,QX, C>> preChecks=Lists.<FindingExpectation<EX,QX, C>> newArrayList();
    

	private final ExpectationChecker<C> simpleCheckerForContext;

    public DefaultExpectationChecker(C context) {
        super(context);
        this.simpleCheckerForContext = checkerFor(context);
    }

    public void addUltimateCauseExpectationCheck(FindingExpectation<EX,QX, C> defaultExpectation){
        whenNotMetChecks.add(defaultExpectation);
    }
    
    public void addPreCheck(FindingExpectation<EX,QX, C> defaultExpectation){
    	preChecks.add(defaultExpectation);
    }
    	
    @Override
    public <E, Q extends Quantity<E>> ExpectationCheckResult<E, Q> resultOfChecking(
            FindingExpectation<E, Q, C> actualExpectationBeingChecked) {
    	
    	assertPreChecks(actualExpectationBeingChecked);
    	    	
        ExpectationCheckResult<E, Q> resultOfCheckingActualExpectation = super.resultOfChecking(actualExpectationBeingChecked);
        
        
        if(!resultOfCheckingActualExpectation.meetsExpectation())
            return checkForUltimateCause(resultOfCheckingActualExpectation);
        else
            return resultOfCheckingActualExpectation;
    }

	private <E, Q extends Quantity<E>> void assertPreChecks(
			FindingExpectation<E, Q, C> actualExpectationBeingChecked) {
		ExpectationCheckResult<EX, QX> preCheckResult= resultOfCheckingAll(preChecks);
    	if(!preCheckResult.meetsExpectation()){
    		failWith(newDescription().appendText("While expecting: ")
    				.appendDescriptionOf(actualExpectationBeingChecked)
    				.appendText("\n")
    				.appendDescriptionOf(preCheckResult));
    	}
	}

    public void assertNoUltimateCauseWhile(Description activityDescription){
    	ExpectationCheckResult<EX, QX> resultOfCheckingForUltimateCause = resultOfCheckingAll(whenNotMetChecks);
		if(!resultOfCheckingForUltimateCause.meetsExpectation())
			 failWith(newDescription().appendText("While ")
					 .appendText(activityDescription.toString())
					 .appendText("\n")
					 .appendDescriptionOf(resultOfCheckingForUltimateCause));
    }

	private <E, Q extends Quantity<E>> ExpectationCheckResult<E, Q> checkForUltimateCause(
			ExpectationCheckResult<E, Q> resultOfCheckingActualExpectation) {
		ExpectationCheckResult<EX, QX> resultOfCheckingForUltimateCause = resultOfCheckingAll(whenNotMetChecks);
		 if(!resultOfCheckingForUltimateCause.meetsExpectation())
			return resultFor(resultOfCheckingForUltimateCause);
		else
		     return resultOfCheckingActualExpectation;
	}
    
	private <E, Q extends Quantity<E>> ExpectationCheckResult<E, Q> resultFor(
			ExpectationCheckResult<EX, QX> resultOfCheckingDefaultExpectations) {
		{
		     StringDescription dissatisfactionDescription = new StringDescription();
		     dissatisfactionDescription
		         .appendDescriptionOf(resultOfCheckingDefaultExpectations);
		 return unsatisfactoryResult(null, dissatisfactionDescription);
		 }
	}


    private  ExpectationCheckResult<EX, QX> resultOfCheckingAll(List<FindingExpectation<EX,QX, C>> expectations) {
    	
        for (FindingExpectation<EX, QX, C> defaultExpectation : expectations) {
			ExpectationCheckResult<EX, QX> resultOfCheckingForExpectation = 
					simpleCheckerForContext.resultOfChecking(defaultExpectation);
            if (!resultOfCheckingForExpectation.meetsExpectation()) {
                return resultOfCheckingForExpectation;
            }
        }
        return ExpectationCheckResult.<EX,QX>satisfactoryResult(null);
    }

    public static <EX, QX extends Quantity<EX>, C> DefaultExpectationChecker<EX, QX, C> defaultExpectationCheckingChecker(C context){
        return new DefaultExpectationChecker<EX, QX, C>(context);
    }

    public void removePreChecks() {
        this.preChecks.clear();
    }
}
