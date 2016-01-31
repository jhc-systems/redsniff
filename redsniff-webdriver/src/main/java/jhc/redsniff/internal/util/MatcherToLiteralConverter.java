package jhc.redsniff.internal.util;

import org.hamcrest.Matcher;

public final class MatcherToLiteralConverter {

    public static String literalStringFrom(Matcher<String> matcher){
        if (matcher instanceof org.hamcrest.core.IsEqual) {
            return withoutOuterQuotes(matcher.toString());//relies on description in IsEqual staying as it is..
        } else if (matcher instanceof org.hamcrest.core.Is)
            return withoutIS(matcher.toString());
        else return null;
    }

    private static String withoutOuterQuotes(String withQuotes){
        return withQuotes.replaceAll("^\"(.*?)\"$","$1");
    }

    private static String withoutIS(String withIs) {
        return withIs.replaceAll("^is \"(.*?)\"$","$1");
    }

}
