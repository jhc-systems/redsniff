package jhc.redsniff.internal.matchers;

import static org.hamcrest.CoreMatchers.containsString;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;

public class ContainsIgnoringWhitespace extends
		TypeSafeMatcher<String> {
	private final String expected;

	public ContainsIgnoringWhitespace(String expected) {
		this.expected = expected;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(expected); //don't bother to add the fact that it contains ignoring case
		
	}

	@Override
	protected boolean matchesSafely(String actual) {
		String strippedActual = stripSpace(actual);
		String strippedExpected =  stripSpace(expected);
		return containsString(strippedExpected).matches(strippedActual); 
	}

	private String stripSpace(String string) {
		return new IsEqualIgnoringWhiteSpace("").stripSpace(string);
	}

	public static Matcher<String> containsIgnoringWhitespace(final String expected){
		return new ContainsIgnoringWhitespace(expected);
			
	}
}