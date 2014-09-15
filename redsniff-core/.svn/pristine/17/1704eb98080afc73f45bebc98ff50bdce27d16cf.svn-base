package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.Describer.newDescription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public final class MatcherUtil {
    
    private MatcherUtil() {
    }

    public static <E> boolean matchAndDiagnose(Matcher<E> subMatcher, E actual, Description mismatchDescription) {
        return matchAndDiagnose(subMatcher, actual, mismatchDescription, "");
    }

    public static <E> boolean matchAndDiagnose(Matcher<E> subMatcher, E actual, Description mismatchDescription,
            String prefixText) {
        if (subMatcher instanceof CheckAndDiagnoseTogetherMatcher)
            return matchAndDiagnoseInOne((CheckAndDiagnoseTogetherMatcher<E>) subMatcher, actual, mismatchDescription,
                    prefixText);
        else
            return matchNormalWay(subMatcher, actual, mismatchDescription, prefixText);
    }
   
    public static <E> boolean matchAndDiagnose(Matcher<E> subMatcher, E actual, Description mismatchDescription,
    		PrefixTextProvider prefixTextProvider) {
        if (subMatcher instanceof CheckAndDiagnoseTogetherMatcher)
            return matchAndDiagnoseInOne((CheckAndDiagnoseTogetherMatcher<E>) subMatcher, actual, mismatchDescription,
            		prefixTextProvider);
        else
            return matchNormalWay(subMatcher, actual, mismatchDescription, prefixTextProvider);
    }
    
    
    
    public static interface PrefixTextProvider {
    	public String getText();
    }

    private static <E> boolean matchAndDiagnoseInOne(CheckAndDiagnoseTogetherMatcher<E> subMatcher, E actual,
            Description mismatchDescription, PrefixTextProvider prefixTextProvider) {
        Description tempDescription = newDescription();
        boolean matches = subMatcher.checkAndDiagnose(actual, tempDescription);
        if (!matches)
            mismatchDescription.appendText(prefixTextProvider.getText())
                    .appendText(tempDescription.toString());
        return matches;
    }
    
    private static <E> boolean matchAndDiagnoseInOne(CheckAndDiagnoseTogetherMatcher<E> subMatcher, E actual,
            Description mismatchDescription, String prefixText) {
        Description tempDescription = newDescription();
        boolean matches = subMatcher.checkAndDiagnose(actual, tempDescription);
        if (!matches)
            mismatchDescription.appendText(prefixText)
                    .appendText(tempDescription.toString());
        return matches;
    }

    private static <E> boolean matchNormalWay(Matcher<E> subMatcher, E actual, Description mismatchDescription,
    		PrefixTextProvider prefixTextProvider) {
        boolean matches = subMatcher.matches(actual);
        if (!matches) {
            mismatchDescription.appendText(prefixTextProvider.getText());
            subMatcher.describeMismatch(actual, mismatchDescription);
        }
        return matches;
    }
    
    private static <E> boolean matchNormalWay(Matcher<E> subMatcher, E actual, Description mismatchDescription,
            String prefixText) {
        boolean matches = subMatcher.matches(actual);
        if (!matches) {
            mismatchDescription.appendText(prefixText);
            subMatcher.describeMismatch(actual, mismatchDescription);
        }
        return matches;
    }

    // There are existing versions of this but all seem to come in big libraries
    // don't want to have dependency on
    public static <T> Collection<T> select(Matcher<? extends T> matcher, Collection<T> fullList) {
        List<T> filteredList = new ArrayList<T>();
        for (T item : fullList) {
            if (matcher.matches(item))
                filteredList.add(item);
        }
        return filteredList;
    }
}
