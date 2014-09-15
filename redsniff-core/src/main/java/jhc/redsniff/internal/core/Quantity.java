package jhc.redsniff.internal.core;

public interface Quantity<E> extends Iterable<E>{

	public abstract Object get();
	public boolean hasAmount();
}
