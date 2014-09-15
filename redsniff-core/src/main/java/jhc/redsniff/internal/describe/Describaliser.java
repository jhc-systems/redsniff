package jhc.redsniff.internal.describe;

import org.hamcrest.SelfDescribing;
/**
 * Turns a thing into a {@link SelfDescribing} thing
 * @author Nic
 *
 * @param <T>
 */
public interface Describaliser<T> {
	SelfDescribing describable(T thing);
}