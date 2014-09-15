package jhc.redsniff.internal.core;

public interface TypedQuantity<E, L> extends Quantity<E> {
	public abstract L get();
}
