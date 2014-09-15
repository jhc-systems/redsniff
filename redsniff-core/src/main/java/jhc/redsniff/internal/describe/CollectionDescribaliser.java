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
package jhc.redsniff.internal.describe;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Collections2.transform;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import com.google.common.base.Function;

public class CollectionDescribaliser<T> implements Describaliser<Collection<T>> {
	
	private Describaliser<T> itemDescribaliser;
	public CollectionDescribaliser(Describaliser<T> itemDescribaliser){
		this.itemDescribaliser = itemDescribaliser;
	}
	@Override
	public SelfDescribing describable(final Collection<T> collection) {
		return new SelfDescribing() {
			
			@Override
			public void describeTo(Description description) {
				if(collection!=null && !collection.isEmpty()) {
						describeElementsTo(description,collection);
				}				
			}
			
			 Function<T,SelfDescribing> TO_DESCRIBABLE_ELEMENT = new Function<T, SelfDescribing>() {
			    	@Override
			    	public SelfDescribing apply(T element) {
			    		return itemDescribaliser.describable(element);
			    	}
			    };
			 Function<SelfDescribing, String> TO_DESCRIPTION_STRING = new Function<SelfDescribing, String>() {
					public String apply(SelfDescribing selfDescribing){
						return StringDescription.asString(selfDescribing);
					}
				};
			 private  void describeElementsTo(Description description, Collection<T> elements) {
				 Collection<String> describableElements = transform(transform(elements, TO_DESCRIBABLE_ELEMENT), TO_DESCRIPTION_STRING);
				 description
			    		.appendText("\n[ ")
			    		.appendText(on(" ], [ ").join(describableElements))
			    		.appendText(" ]");
		    }
		};
	}
}