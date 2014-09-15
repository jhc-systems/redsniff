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

import static jhc.redsniff.internal.core.Item.oneOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionOf<E> implements Collection<E>, TypedQuantity<E,Collection<E>>    {

	private Collection<E> collection;
	public CollectionOf(Collection<E> collection){
		this.collection = collection;
	}
	
	@Override
	public Collection<E> get() {
		return collection;
	}
	
	
	@Override
	public boolean add(E e) {
		return collection.add(e);
	}
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return collection.addAll(c);
	}
	@Override
	public void clear() {
		collection.clear();		
	}
	@Override
	public boolean contains(Object o) {
		return collection.contains(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}
	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	@Override
	public Iterator<E> iterator() {
		return collection.iterator();
	}
	@Override
	public boolean remove(Object o) {
		return collection.remove(o);
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		return collection.removeAll(c);
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		return collection.retainAll(c);
	}
	@Override
	public int size() {
		return collection.size();
	}
	@Override
	public Object[] toArray() {
		return collection.toArray();
	}
	@Override
	public <T> T[] toArray(T[] a) {
		return collection.toArray(a);
	}

	public static <E> CollectionOf<E> empty() {
		return collectionOf(Collections.<E>emptyList());
	}
	
	public static <E> CollectionOf<E> fresh() {
	    return collectionOf(new ArrayList<E>());
	}
	
	public static <E> CollectionOf<E> collectionOf(Collection<E> collection){
		return new CollectionOf<E>(collection);
	}
	
	public static <E> CollectionOf<E> collectionOf(E singleItem){
		List<E> list = new ArrayList<E>(1);
		list.add(singleItem);
		return new CollectionOf<E>(list);
	}
	public Item<E> first(){
		return oneOf(collection.isEmpty()?null:collection.iterator().next());
	}

    @Override
    public boolean hasAmount() {
       return !isEmpty();
    }
	
}
