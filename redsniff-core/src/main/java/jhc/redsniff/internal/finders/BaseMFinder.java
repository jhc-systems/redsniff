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
package jhc.redsniff.internal.finders;

import static jhc.redsniff.internal.finders.OnlyFinder.only;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.finders.within.MFinderWithinMFinder;
import jhc.redsniff.internal.finders.within.MFinderWithinSFinder;

import org.hamcrest.Matcher;

public abstract class BaseMFinder<E, C> implements MFinder<E,C>{

	@Override
	public MFinder<E, C> that(Matcher<? super E> elementMatcher) {
		return new MFinderThatHasCondition<>(this,  elementMatcher);
	}
	
	protected MFinder<E, C> thatOpt(Matcher<? super E> elementMatcher) {
		return new MFinderThatHasCondition<>(this,  elementMatcher, true);
	}
	
	
	@Override
    public <OE extends C, OC> MFinder<E, OC> withinA(MFinder<OE, OC> scopeFinder) {
        return MFinderWithinMFinder.finderWithin(this, scopeFinder);
    }

    @Override
    public <OE extends C, OC> MFinder<E, OC> withinThe(SFinder<OE, OC> scopeFinder) {
        return MFinderWithinSFinder.finderWithin(this, scopeFinder);
    }
    
    @Override
    public <OE extends C, OC> MFinder<E, OC> withinThe(MFinder<OE, OC> scopeFinder) {
        return withinThe(only(scopeFinder));
    }

	@Override
	public MFinder<E, C> optimizedWith(Matcher<? super E> matcher) {
		return this.thatOpt(matcher);
	}

	@Override
	public String toString() {
		return Describer.asString(this);
	}
    
}