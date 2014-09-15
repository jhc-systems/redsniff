package jhc.redsniff.webdriver;

import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.Finders.dropDown;
import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.webdriver.Finders.form;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasCssClass;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasId;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasTagName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import static jhc.redsniff.webdriver.WebDriverMatchers.isString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.finders.BaseMFinder;
import jhc.redsniff.internal.finders.LocatorFinder;

import org.hamcrest.Description;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class OptimizationTests {

	@Test
	public void optimizesByMostSpecificMatcher1Level() {
		MFinder<WebElement, SearchContext> div123 = div().that(hasId("123"));
		assertHasDescription(div123, "div that has id \"123\"");
		assertHasOptimizedDescription(div123,
				"element with id \"123\" that has tagname \"div\"");
	}
	
	@Test
	public void cantOptimizeWhereNonLiteralUsedForLocator(){
		assertHasOptimizedDescription(div().that(hasId(startsWith("1"))), 
				"div that has id a string starting with \"1\"");
	}

	@Test
	public void optimizesByMostSpecificMatcher2Levels() {
		String expected = "element with id \"123\" that: {has name \"bob\" and has tagname \"div\"}";
		assertHasOptimizedDescription(
				div().that(hasName("bob")).that(hasId("123")), expected);
		assertHasOptimizedDescription(
				div().that(hasId("123")).that(hasName("bob")), expected);
		assertHasOptimizedDescription(
				elementFound(By.id("123")).that(hasTagName("div")).that(
						hasName("bob")), expected);
		assertHasOptimizedDescription(
				elementFound(By.id("123")).that(hasName("bob")).that(
						hasTagName("div")), expected);
	}
	
	@Test
	public void cantFullyOptimizeWhereNonLiteralUsedForLocator2Matchers(){
		assertHasOptimizedDescription(div().that(hasId(startsWith("1"))).that(hasName("bob")), 
				"div that: {has id a string starting with \"1\" and has name \"bob\"}");
	}
	
	@Test
	public void optimizesItemWithinAnother() {
		MFinder<WebElement, SearchContext> divInForm = div().that(
				hasName("bob")).withinA(form().that(hasName("orderForm")));

		assertHasDescription(divInForm, "div that has name \"bob\", "
				+ "within a form that has name \"orderForm\"");
		assertHasOptimizedDescription(
				divInForm,
				"element with name \"bob\" that has tagname \"div\","
						+ " within a element with name \"orderForm\" that has tagname \"form\"");

		assertHasOptimizedDescription(
				divInForm.that(hasId("123")),
				"element with id \"123\" that: {has name \"bob\" and has tagname \"div\"},"
						+ " within a element with name \"orderForm\" that has tagname \"form\"");
	}


	@Test
	public void describesFinderWithOneMatcher() {
		assertHasDescription(dropDown().that(hasName("a_nonexistent-drop-down")),
				"drop-down that has name \"a_nonexistent-drop-down\"");
	}

	@Test
	public void describesFinderWithTwoMatchers() {
		assertHasDescription(
				dropDown()
					.that(hasName("a_nonexistent-drop-down"))
					.that(hasCssClass("nope")),
				"drop-down that: {has name \"a_nonexistent-drop-down\" and has css class \"nope\"}");
	}

	@Test
	public void describesFinderWithSeveralMatchers() {
		assertHasDescription(
				dropDown().that(hasName("a_nonexistent-drop-down"))
						.that(hasCssClass("nope")).that(hasId("1"))
						.that(hasText("bob")),
				"drop-down that: {has name \"a_nonexistent-drop-down\" and has css class \"nope\" and has id \"1\" and has text \"bob\"}");
	}

	public void assertHasOptimizedDescription(
			MFinder<WebElement, SearchContext> finder, String expected) {
		assertHasDescription(finder.asOptimizedFinder(), expected);
	}

	@Test
	public void swapsies() {
		MFinder<WebElement, SearchContext> div = div();
		MFinder<WebElement, SearchContext> swapped = ((LocatorFinder<WebElement, SearchContext>) div)
				.optimizedWith(hasId("123"));

		assertHasDescription(div, "div");
		assertHasDescription(swapped,
				"element with id \"123\" that has tagname \"div\"");
	}

	@Test
	public void withinThatSameAsThatWithin() {
		String expected = "drop-down that has name \"a_drop-down\", within a form";
		assertHasDescription(
				dropDown().withinA(form()).that(hasName("a_drop-down")),
				expected);
		assertHasDescription(
				dropDown().that(hasName("a_drop-down")).withinA(form()),
				expected);
	}
	
	
	

	// TODO move up to master base class for all tests
	protected <E, Q extends Quantity<E>, C> void assertHasDescription(
			Finder<E, Q, C> finder, String expected) {
		Description description = Describer.newDescription();
		finder.describeTo(description);
		assertThat(description.toString(), isString(expected));
	}

	
	
}