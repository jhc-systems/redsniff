package jhc.redsniff.internal.describe;

import jhc.redsniff.internal.core.Item;

import org.hamcrest.SelfDescribing;

public class ItemDescribaliser<T> implements Describaliser<Item<T>>{

	private Describaliser<T> innerDescribaliser;
	public ItemDescribaliser(Describaliser<T> innerDescribaliser){
		this.innerDescribaliser = innerDescribaliser;
		
	}
	@Override
	public SelfDescribing describable(Item<T> thing) {
		return innerDescribaliser.describable(thing.get());
	}
	
}