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

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Transformer;

import org.hamcrest.Description;

public class TransformingContextSFinder<T,C> extends BaseSFinder<T, C>{

    private final Transformer<C,T> transformer;

    public TransformingContextSFinder(Transformer<C,T> transformer) {
        this.transformer = transformer;
    }

    @Override
    public Item<T> findFrom(C element, Description notFoundDescription) {
        return Item.oneOf(transformer.transform(element, notFoundDescription));
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(transformer);
    }

	@Override
	public SFinder<T, C> asOptimizedFinder() {
		return this;
	}

}
