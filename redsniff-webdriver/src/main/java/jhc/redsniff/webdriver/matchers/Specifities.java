package jhc.redsniff.webdriver.matchers;

import jhc.redsniff.internal.locators.MatcherLocator;

import javax.swing.text.html.HTML;
import java.util.Arrays;
import java.util.List;

public class Specifities {

    private static final List<Class<? extends MatcherByLocator>> SPECIFICITES_RANK=
            Arrays.asList(
                    TagNameMatcher.class, //90
                    CssClassNameMatcher.class, //91
                    AttributeMatcher.class, //93
                    NameMatcher.class, //95
                    IdMatcher.class //99
            );

    public static int specifityOf(Class<? extends MatcherByLocator> matcherLocatorClass){
        return SPECIFICITES_RANK.indexOf(matcherLocatorClass);
    }
}
