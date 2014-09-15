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
package jhc.redsniff.internal.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.internal.ReflectiveTypeFinder;

//Copied from hamcrest so it can extend CheckAndDiagnoseTogetherMatcher - not sure if we need it now really or can just simplify things using bean matchers or something
public abstract class FeatureMatcher<T, U> extends CheckAndDiagnoseTogetherMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
    private final Matcher<? super U> subMatcher;
    private final String featureDescription;
    private final String featureName;

    /**
     * Constructor
     * 
     * @param subMatcher
     *            The matcher to apply to the feature
     * @param featureDescription
     *            Descriptive text to use in describeTo
     * @param featureName
     *            Identifying text for mismatch message
     */
    public FeatureMatcher(Matcher<? super U> subMatcher, String featureDescription, String featureName) {
        super(TYPE_FINDER);
        this.subMatcher = subMatcher;
        this.featureDescription = featureDescription;
        this.featureName = featureName;
    }

    /**
     * Implement this to extract the interesting feature.
     * 
     * @param actual
     *            the target object
     * @return the feature to be matched
     */
    protected abstract U featureValueOf(T actual);

    @Override
    protected boolean matchesSafely(T actual, Description mismatch) {
        return matchAndDiagnose(subMatcher, featureValueOf(actual), mismatch, featureName + " ");
    };

    @Override
    public final void describeTo(Description description) {
        description.appendText(featureDescription).appendText(" ")
                .appendDescriptionOf(subMatcher);
    }
}
