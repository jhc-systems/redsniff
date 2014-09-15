package jhc.redsniff.internal.matchers;

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.SelfDescribing;

public abstract class FilterResult<E> implements SelfDescribing{

    private final CollectionOf<E> foundElements;

    public FilterResult(CollectionOf<E> foundElements){
    	this.foundElements = foundElements;
    }
    
    public  boolean isInvalid(){
    	return false;
    }

    public CollectionOf<E> foundElements() {
        return foundElements;
    }
}
