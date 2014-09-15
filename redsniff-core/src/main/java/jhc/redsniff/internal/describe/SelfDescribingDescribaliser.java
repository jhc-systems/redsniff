package jhc.redsniff.internal.describe;


import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class SelfDescribingDescribaliser implements Describaliser<SelfDescribing>{
	@Override
	public SelfDescribing describable(final SelfDescribing thing) {
		return new SelfDescribing() {
			@Override
			public void describeTo(Description description) {
				description.appendDescriptionOf(thing);
			}
		};
	}
}