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
