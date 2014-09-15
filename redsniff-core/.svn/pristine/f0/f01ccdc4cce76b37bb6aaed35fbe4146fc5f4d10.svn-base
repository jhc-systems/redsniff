package jhc.redsniff.internal.describe;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class ObjectDescribaliser implements Describaliser<Object> {

	@Override
	public SelfDescribing describable(final Object thing) {
		return new SelfDescribing() {
			@Override
			public void describeTo(Description description) {
				description.appendText(thing.toString());
			}
		};
	}
}