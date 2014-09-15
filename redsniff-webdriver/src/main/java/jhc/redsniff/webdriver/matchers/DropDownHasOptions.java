package jhc.redsniff.webdriver.matchers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static jhc.redsniff.webdriver.finders.HtmlTagFinders.dropDownOption;

import java.util.List;

import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

/**
 * 
 * TODO _ eventually make use of thing that matches all found results to list of
 * finders
 * 
 * @author Nic
 * 
 */
public class DropDownHasOptions extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private final Matcher<Iterable<? extends String>> optionListMatcher;

    public DropDownHasOptions(Matcher<Iterable<? extends String>> optionListMatcher) {
        this.optionListMatcher = optionListMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" select options: ")
                .appendDescriptionOf(optionListMatcher);
    }

    @Override
    protected boolean matchesSafely(WebElement selectItem, Description mismatchDescription) {
        List<String> textFromOptions = textFromOptions(selectItem);
        return matchAndDiagnose(optionListMatcher, textFromOptions, mismatchDescription);
    }

    public static List<String> textFromOptions(WebElement selectItem) {
     // TODO this shouldn't call findFrom directly
        List<WebElement> optionElements = newArrayList(dropDownOption().findFrom(selectItem, Description.NONE)); 
        return transform(optionElements, new Function<WebElement, String>() {
            @Override
            public String apply(WebElement optionElement) {
                return optionElement.getText();
            }
        });
    }

    
    private static Matcher<Iterable<? extends String>> isIterableContainingInOrder(final List<Matcher<? super String>> optionTextMatcherList){
        return new IsIterableContainingInOrder<String>(optionTextMatcherList) {
            @Override
            public void describeTo(Description description) {
                description.appendList("[", ", ", "]", optionTextMatcherList).appendText(" in that order");
            }
        };
    }
    private static Matcher<Iterable<? extends String>> isIterableContainingInAnyOrder(final List<Matcher<? super String>> optionTextMatcherList) {
        return new IsIterableContainingInAnyOrder<String>(optionTextMatcherList) {
            @Override
            public void describeTo(Description description) {
                description.appendList("[", ", ", "]", optionTextMatcherList).appendText(" in any order");
            }
        };
    }
    

    @Factory
    public static Matcher<WebElement> hasOptionsInAnyOrder(String... optionText) {
        return new DropDownHasOptions(isIterableContainingInAnyOrder(transform(asList(optionText), TO_STRING_EQUALITY_MATCHER)));
    }


    @SafeVarargs
	@Factory
    public static Matcher<WebElement> hasOptionsInAnyOrder(Matcher<? super String>... optionTextMatchers) {
        return new DropDownHasOptions(isIterableContainingInAnyOrder(asList(optionTextMatchers)));
    }

    
    @Factory
    public static Matcher<WebElement> hasOptions(String... optionText) {
        return new DropDownHasOptions(isIterableContainingInOrder(transform(asList(optionText), TO_STRING_EQUALITY_MATCHER)));
    }

    @SafeVarargs
	@Factory
    public static Matcher<WebElement> hasOptions(Matcher<? super String>... optionTextMatchers) {
        return new DropDownHasOptions(isIterableContainingInOrder(asList(optionTextMatchers)));
    }

    private static final Function<String, Matcher<? super String>> TO_STRING_EQUALITY_MATCHER = new Function<String, Matcher<? super String>>() {
        @Override
        public Matcher<? super String> apply(String input) {
            return isString(input);
        }
    };
    // //TODO - should probably do this with finders to be more flexible
    // public static Matcher<WebElement> selectedOption(String optionText) {
    // return new SelectedDropDownOptionMatcher(equalTo(optionText));
    // }
    //
    // public static Matcher<WebElement> selectedOption(Matcher<? super String>
    // optionTextMatcher) {
    // return new SelectedDropDownOptionMatcher(optionTextMatcher);
    // }

}
