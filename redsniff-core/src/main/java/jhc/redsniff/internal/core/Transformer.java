package jhc.redsniff.internal.core;


import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public abstract class Transformer<E, T> implements SelfDescribing{

	public abstract T transform(E element, Description couldNotTransformDescription);
	
}
