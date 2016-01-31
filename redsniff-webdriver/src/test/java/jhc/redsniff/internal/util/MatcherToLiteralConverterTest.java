package jhc.redsniff.internal.util;

import org.hamcrest.Matchers;
import org.junit.Test;

import static jhc.redsniff.internal.util.MatcherToLiteralConverter.literalStringFrom;
import static org.junit.Assert.assertEquals;

public class MatcherToLiteralConverterTest {

    @Test
    public void shouldExtractLiteralFromLiteralIsMatcher() {
        assertEquals("Blah",
                literalStringFrom(Matchers.is("Blah")));
    }

    @Test
    public void shouldExtractLiteralFromEqualMatcher() {
        assertEquals("Blah",
                literalStringFrom(Matchers.equalTo("Blah")));
    }

}