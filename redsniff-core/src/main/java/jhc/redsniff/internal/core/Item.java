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
package jhc.redsniff.internal.core;

import java.util.Iterator;

import com.google.common.collect.Iterators;

public class Item<E> implements TypedQuantity<E, E> {

	
	private E item;
	public Item(E item){
		this.item = item;
	}
	@Override
	public E get() {
		return item;
	}
	
	@Override
	public boolean hasAmount(){
		return item!=null;
	}
	public static <E> Item<E> oneOf(E item){
		return new Item<E>(item);
	}
	
	public static <E> Item<E> nothing(){
	    return new Item<E>(null);
	}
    @Override
    public Iterator<E> iterator() {
       return Iterators.singletonIterator(item);
    }
}
