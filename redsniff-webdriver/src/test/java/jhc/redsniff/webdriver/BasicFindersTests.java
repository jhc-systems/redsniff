/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jhc.redsniff.webdriver;
import static jhc.redsniff.core.FindingExpectations.presenceOf;
import static jhc.redsniff.internal.finders.AsActionableFinder.each;
import static jhc.redsniff.internal.finders.OnlyFinder.only;
import static jhc.redsniff.internal.matchers.ContainsIgnoringWhitespace.containsIgnoringWhitespace;
import static jhc.redsniff.matchers.numerical.NumericalMatchers.atLeast;
import static jhc.redsniff.webdriver.Finders.$;
import static jhc.redsniff.webdriver.Finders.attribute;
import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.Finders.dropDown;
import static jhc.redsniff.webdriver.Finders.dropDownOption;
import static jhc.redsniff.webdriver.Finders.elementFound;
import static jhc.redsniff.webdriver.Finders.fifth;
import static jhc.redsniff.webdriver.Finders.first;
import static jhc.redsniff.webdriver.Finders.form;
import static jhc.redsniff.webdriver.Finders.fourth;
import static jhc.redsniff.webdriver.Finders.last;
import static jhc.redsniff.webdriver.Finders.second;
import static jhc.redsniff.webdriver.Finders.span;
import static jhc.redsniff.webdriver.Finders.tableElement;
import static jhc.redsniff.webdriver.Finders.textElement;
import static jhc.redsniff.webdriver.Finders.textbox;
import static jhc.redsniff.webdriver.Finders.third;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasCssClass;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasId;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasSubElement;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import static jhc.redsniff.webdriver.WebDriverMatchers.isDisabled;
import static jhc.redsniff.webdriver.WebDriverMatchers.isEnabled;
import static jhc.redsniff.webdriver.WebDriverMatchers.isString;
import static jhc.redsniff.webdriver.matchers.ColorMatcher.hasColor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.openqa.selenium.lift.match.NumericalMatchers.exactly;

import java.util.Collection;
import java.util.Iterator;

import jhc.redsniff.RedsniffTestBase;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.FindingExpectations;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.expectations.ExpectationChecker;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.finders.BaseMFinder;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.ContainsIgnoringWhitespace;
import jhc.redsniff.matchers.numerical.NumericalMatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class BasicFindersTests extends RedsniffTestBase {

	@Test	public void 
	confirmsPresenceOfElementWhenPresent(){
		t.assertPresenceOf(dropDown().that(hasName("a_drop-down")));
	}
	
	@Test public void
    describesReasonNotFoundWhenNoBaseItemAtAll(){
    	expectError("Expected: a div that has name a string starting with \"X\"\n" +
    			"but:\n" +
    			"Not found - No div found at all");
    	t.assertPresenceOf(div().that(hasName(startsWith("X"))));
    	t.assertPresenceOf($("div.myClass"));
    }
	
	@Test public void
    describesReasonNotFoundWhenNoBaseItemAtAllOptimizing(){
    	expectError("Expected: a div that has id  \"XXX\"\n" +
    			"but:\n" +
    			"Not found - No div found at all");
    	t.assertPresenceOf(div().that(hasId("XXX")));
    }
	
	@Ignore("TODO")
	@Test public void
    failFastOnWaiting(){
    	expectError("Expected: a div that has id  \"XXX\"\n" +
    			"but:\n" +
    			"Not found - No div found at all");
    	t.waitFor(div().that(hasId("XXX")));
    }
	
	
	@Test public void 
	 describesReasonWhenSoleMatcherNotMatchedOneCandidate(){
	    	expectError(
	    			"Expected: a input that has name \"search\"\n"+
	    			"but:\n" +
	    			"Not found - \n" +
	    			"<input> (name:Search)  - not matched because name was:\n"+
	    			"   '[S]earch'\n"+
	    			"instead of:\n" +
	    			"   '[s]earch'\n");
	    	
		t.assertPresenceOf(elementFound(By.tagName("input"))
				.that(hasName("search")));
	}
	
	@Test public void 
	 describesReasonWhenLastMatcherNotMatched(){
		 expectError(
		 "Expected: a form that: {has name \"theForm\" and has css class \"NotClass\"}\n" +
		 "but:\n" +
		 "Not found - \n" +
		 "<form> (class:form) (name:theForm) (id:frmId) OPTION A OPTION 1 OPTION 2 OPTION 3 - not matched because class was:\n" +
		 "   '[f]o[rm]'\n" +
		 "instead of:\n" +
		 "   '[N]o[tClass]'");
		 
		t.assertPresenceOf(form()
							.that(hasName("theForm"))
							.that(hasCssClass("NotClass")));
	}
	 
	 @Test public void 
	 describesAllNearestMatchesAndReasonsWhereSeveralCandidates(){
		expectError(
				 "Expected: a drop-down that has name \"a_nonexistent-drop-down\"\n" +
						 "but:\n" +
						 "Not found - nearest matches:\n");
		expectError(
						"<select> (name:a_drop-down) OPTION A - not matched because name was:\n" +
						"   'a_[]drop-down'\n" +
						"instead of:\n" +
				 		"   'a_[nonexistent-]drop-down'");
		expectError(
		 				"<select> (name:a_drop-down_with_3_options) OPTION 1 OPTION 2 OPTION 3 - not matched because name was:\n" +
		 				"	'a_[]drop-down[_with_3_options]'\n"+
		 				"instead of:\n" +
		 				"	'a_[nonexistent-]drop-down[]'");
		expectError(
						"<select> (name:a_drop-down) OPTION AA - not matched because name was:\n" +
						"	'a_[]drop-down'\n"+
						"instead of:\n" +
						"	'a_[nonexistent-]drop-down'");
		expectError(
						"<select> (name:another_drop-down_with_3_options) OPTION 10 OPTION 20 OPTION 30 - not matched because name was:\n" +
						"	'a[nother_drop-down_with_3_options]'\n"+
						"instead of:\n" +
						"	'a[_nonexistent-drop-down]'");
		t.assertPresenceOf(dropDown().that(hasName("a_nonexistent-drop-down")));
	 }

	 @Test public void 
	 describesNearestMatchWhereNotFoundSeveralMatchers(){
		expectError(
				 "Expected: a drop-down that: {has name \"a_nonexistent-drop-down\" and has css class \"nope\"}\n" +
						 "but:\n" +
						 "Not found - but searching for drop-down that has name \"a_nonexistent-drop-down\"\n"+
						 "nearest matches:\n");
		expectError(
						"<select> (name:a_drop-down) OPTION A - not matched because name was:\n" +
						"   'a_[]drop-down'\n" +
						"instead of:\n" +
				 		"   'a_[nonexistent-]drop-down'");
		expectError(
		 				"<select> (name:a_drop-down_with_3_options) OPTION 1 OPTION 2 OPTION 3 - not matched because name was:\n" +
		 				"	'a_[]drop-down[_with_3_options]'\n"+
		 				"instead of:\n" +
		 				"	'a_[nonexistent-]drop-down[]'");
		expectError(
						"<select> (name:a_drop-down) OPTION AA - not matched because name was:\n" +
						"	'a_[]drop-down'\n"+
						"instead of:\n" +
						"	'a_[nonexistent-]drop-down'");
		expectError(
						"<select> (name:another_drop-down_with_3_options) OPTION 10 OPTION 20 OPTION 30 - not matched because name was:\n" +
						"	'a[nother_drop-down_with_3_options]'\n"+
						"instead of:\n" +
						"	'a[_nonexistent-drop-down]'");
		 
		 t.assertPresenceOf(dropDown().that(hasName("a_nonexistent-drop-down")).that(hasCssClass("nope")));
	 }
	 
	 
	 
	 //Absence
	 @Test	public void 
	 confirmsAbsenceOfElementWhenNotPresent(){
		 t.assertAbsenceOf(dropDown().that(hasName("a_nonexistent-drop-down")));
	 }

	 @Test public void 
	 complainsWhenPresentIfAssertedAbsent() {
		 expectError(
				 "Expected: not to find any form that has name \"theForm\"\n"
						 + "but:\n"
						 + "did find:\n"
						 + "[ <form> (class:form) (name:theForm) (id:frmId) OPTION A OPTION 1 OPTION 2 OPTION 3 ]");
		 t.assertAbsenceOf(form().that(hasName("theForm")));
	 } 

	
	 //Quantity
	 @Test public void
	 checksQuantityFound(){
		 t.assertPresenceOf(exactly(2), form().that(hasCssClass("form")));
	 }
	 
	 @Test public void
	 checksMinQuantityFound(){
		 t.assertPresenceOf(atLeast(1), form().that(hasCssClass("form")));
	 }
	 
	 @Test public void
	 describesActualQuantityFound(){
		 expectError(	"Expected: there to be <3> of form that has css class \"form\"\n" +
				 		"but:\n" +
				 		"found <2> such elements");
		 t.assertPresenceOf(exactly(3), form().that(hasCssClass("form")));
	 }
	 
	 
	//Ordinals
	@Test  public void 
    confirmsPresenceOfSecondItem(){
        t.assertPresenceOf(second(dropDown().that(hasName("a_drop-down"))));
    }
	
	@Test  public void 
	complainsNotEnoughForThirdItem(){
	    expectError("Expected: a 3rd drop-down that has name \"a_drop-down\"\n" +
	    		"but:\n" +
	    		"only found <2> such elements");
        t.assertPresenceOf(third(dropDown().that(hasName("a_drop-down"))));
    }

	@Test public void
	findsNthItemWhenEnoughPresent(){
		//html has 4 spans that has numbered text
		t.assertThatThe(first(span().that(hasCssClass("span_class"))), 
				hasText("span1Text"));
		t.assertThatThe(third(span().that(hasCssClass("span_class"))),
				hasText("span3Text"));
		t.assertThatThe(last(span().that(hasCssClass("span_class"))), 
				hasText("span4Text"));
		t.clickOn(fourth(span().that(hasCssClass("span_class"))));
	}

	@Test public void
	complainsNotEnoughIfAskedForNthItemGreaterThanNumberFound(){
		//html has 4 spans that has numbered text - asking for 5th
		expectError(
				"Expected: a 5th span that has css class \"span_class\" to click on\n"+
				"but:\n"+
				"only found <4> such elements\n"+
				"[ <span> (class:span_class) span1Text ], [ <span> (class:span_class) span2Text ], [ <span> (class:span_class) span3Text ], [ <span> (class:span_class) span4Text ]");
		t.clickOn(fifth(span().that(hasCssClass("span_class"))));
	}
	
	
	//using find() to obtain the WebElement(s)
	 
	 @Test public void
		findReturnsElementsAsCollectionOfWebElementsforMFinder(){
		 
			Collection<WebElement> dropDowns = t.find(dropDown().that(hasName("a_drop-down")));
			
			assertThat(dropDowns, hasSize(2));
			Iterator<WebElement> iterator = dropDowns.iterator();
			WebElement dropDown1 = iterator.next();
			WebElement dropDown2 = iterator.next();
			
			assertEquals("select", dropDown1.getTagName());
			assertEquals("select", dropDown2.getTagName());
			
			assertEquals("OPTION A", dropDown1.getText());
			assertEquals("OPTION AA", dropDown2.getText());
	 }
	 
	 @Test public void
		findReturnsSingleElementForOrdinalSFinder(){
		 
			WebElement dropDown1 = t.find(first(dropDown().that(hasName("a_drop-down"))));
			
			assertEquals("select", dropDown1.getTagName());
			assertEquals("OPTION A", dropDown1.getText());
	 }
	 
	 @Test public void
		findReturnsSingleElementForOnlySFinder(){
		 
			WebElement dropDown1 = t.find(only(dropDown().that(hasName("another_drop-down_with_3_options"))));
			
			assertEquals("select", dropDown1.getTagName());
			assertEquals("OPTION 10 OPTION 20 OPTION 30", dropDown1.getText());
	 }
	 
	 
	 @Test public void
		findDescribesErrorWhenNotFound(){
		 
		 	expectError(
				 	"Expected: a unique drop-down that has name \"a_drop-down\"\n" +
				 	"but:\n" +
				 	"Was not unique: drop-down that has name \"a_drop-down\"\n" +
				 	"found <2> such elements\n" +
				 	"[ <select> (name:a_drop-down) OPTION A ], [ <select> (name:a_drop-down) OPTION AA ]");
		 
		 	WebElement dropDown = t.find(only(dropDown().that(hasName("a_drop-down"))));
	 }
	 
	     
	@Test public void
	assertsThatTheChecksSomeMatcherMatchesFindings(){
	    t.assertThatThe(first(dropDown().that(hasName("a_drop-down"))),  isEnabled());
	}
	
	@Test public void
	assertThatTheDescribesMismatchUsingDescriptionOfFinder(){
		expectError("Expected: 1st drop-down that has name \"a_drop-down\" to match  is <disabled>\n"+
					"but:\n" +
					"element was <enabled>");
	    t.assertThatThe(first(dropDown().that(hasName("a_drop-down"))),  isDisabled());
	}

	//Subelement
	@Test public void
	assertsPresenceOfSubElement(){
		t.assertPresenceOf(dropDown().that(hasSubElement(dropDownOption("OPTION A"))));
	}

	@Test public void
	assertPresenceOfSubElementsContainedWithinParent(){
		t.assertPresenceOf(exactly(1), 
				dropDown()
					.that(hasSubElement(dropDownOption("OPTION 2"))));
	}

	
	 
	@Test public void
	findersAreImmutableByThat(){
		MFinder<WebElement, SearchContext> div = div();
		assertHasDescription(div, "div");
		MFinder<WebElement, SearchContext> divNamedBob = div.that(hasName("bob"));
		assertHasDescription(div, "div");
		assertHasDescription(divNamedBob, "div that has name \"bob\"");
		assertNotSame(div, divNamedBob);
	}

	@Test public void 
	usesUnoptimizedToDescribeMismatch() {
		expectError(
    			"Expected: a textbox that has name \"search\"\n"+
    			"but:\n" +
    			"Not found - \n" +
    			"<input> (name:Search)  - not matched because name was:\n"+//TODO take out space
    			"   '[S]earch'\n"+
    			"instead of:\n" +
    			"   '[s]earch'\n");
		t.find(textbox().that(hasName("search")));
	}
	
	
	//Within	
	@Test public void
	confirmsElementWithinAnotherElement(){
		t.assertPresenceOf(dropDown().that(hasName("a_drop-down")).withinA(form().that(hasName("theForm"))));
	}
	
	@Test public void
	confirmsAbsenceOfElementWithinAnotherElement(){
		t.assertAbsenceOf(dropDown().that(hasName("a_drop-down_with_3_options")).withinA(form().that(hasName("theOtherForm"))));
	}
	
	
	@Test public void
	confirmsNumberOfElementsWithinAnotherElementTakingScopeIntoAccount(){
		//there are 2 altogether but only one that's within "theForm"
		
		t.assertPresenceOf(exactly(2), dropDown().that(hasName("a_drop-down")));
		t.assertPresenceOf(exactly(1), dropDown().that(hasName("a_drop-down")) 
											.withinA(form().that(hasName("theForm"))));
	}
	
	@Test public void
	checksRenderedColourOfElement(){
		t.assertPresenceOf(span().that(hasColor("#000")));
	}
	
	@Test public void
	complainsOfElementFoundWhenAbsenceOfElementWithinAnotherElementAsserted(){
		expectError("Expected: not to find any drop-down that has name \"a_drop-down_with_3_options\", within a form that has name \"theForm\"\n" +
				"but:");
		expectError("" +
				"did find:\n" +
				"[ <select> (name:a_drop-down_with_3_options) OPTION 1 OPTION 2 OPTION 3 ]");
		
		t.assertAbsenceOf(dropDown().that(hasName("a_drop-down_with_3_options"))
        .withinA(form().that(hasName("theForm"))));
	}
	
	@Test public void
	complainsWhenOuterElementNotFound(){
		expectError("Expected: a drop-down that has name \"a_drop-down\", " +
				"within a form that has css class \"nonExistentForm\"\n" +
				"but:\n" +
				"Not found - form that has css class \"nonExistentForm\"\n" +
				"within which to search for {drop-down that has name \"a_drop-down\"}");
		t.find(dropDown().that(hasName("a_drop-down"))
        .withinA(form().that(hasCssClass("nonExistentForm"))));
	}
	
	@Test public void
	complainsWhenInnerElementNotFound(){
		expectError("Expected: a drop-down that has name \"a_nonexistent_drop-down\", within a form that has css class \"form\"\n" +
				"but:\n");
		expectError(
				" within: <form> (class:form) (name:theOtherForm) (id:frmId) OPTION AA OPTION 10 OPTION 20 OPTION 30\n" +
				"	Not found - nearest matches:\n" +
				"<select> (name:a_drop-down) OPTION AA - not matched because name was:\n" +
				"   'a_[]drop-down'\n" +
				"instead of:\n" +
				"   'a_[nonexistent_]drop-down'");
		
		t.find(
				dropDown().that(hasName("a_nonexistent_drop-down")) 
					.withinA(form().that(hasCssClass("form"))));
	}
	
	@Test public void
	assertsSeveralLevelsDeepByWrappingOrChainingWithSameErrorMessage() {
		
		String errorMessageWithChaining="";
		String errorMessageWithWrapping="";
		try {
			t.assertPresenceOf(dropDownOption("OPTION X")
            .withinA(
            	dropDown()
            		.withinA(
            			form()
            			.withinA(
            					div()
            			)
            		)
            ));
		}catch (AssertionError e){
			errorMessageWithWrapping = e.getMessage();
		}
		
		try {
			t.assertPresenceOf(dropDownOption("OPTION X")
            .withinA(
            	dropDown()
            )
            .withinA(
            	form()
            )
            .withinA(
            	div()
            ));
		}catch (AssertionError e){
			errorMessageWithChaining = e.getMessage();
		}
		assertThat(errorMessageWithWrapping, isString(errorMessageWithChaining));
		assertThat(errorMessageWithWrapping, isString("" +
				"Expected: a drop-down option that has text \"OPTION X\" within:\n" +
				"	a drop-down within:\n" +
				"		a form, within a div\n" +
				"but:\n" +
				"Not found - div\n" +
				"within which to search for {form}\n" +
				"because\n" +
				"No div found at all"));
		
	}
	
	@Test public void 
	whenExceptionThrownByMatcherUsesExceptionMessageInMismatch(){ 
		expectError(
				"Expected: a span that <matcherDescription>\n" +
				"but:\n" +
				"Not found - nearest matches:\n" +
				"could not check whether {<matcherDescription>} because exception was thrown: <ExampleExceptionMessage>");
		t.assertPresenceOf(span().that(matcherThrowingException()));
	}

	private Matcher<? super WebElement> matcherThrowingException() {
		return new CheckAndDiagnoseTogetherMatcher<WebElement>() {
			
			@Override
			public void describeTo(Description description) {
				description.appendText("<matcherDescription>");			
			}
			
			@Override
			protected boolean matchesSafely(WebElement item,
					Description mismatchDescription) {
				throw new RuntimeException("<ExampleExceptionMessage>");
			}
		};
	}
	
	@Test public void
	testTextElement(){
		t.assertPresenceOf(textElement());
	}

	@Test public void 
	testPartialCssClass(){
	    t.assertThe(presenceOf(tableElement().that(hasCssClass("classA"))));
	    t.assertThe(presenceOf(tableElement().that(hasCssClass("classB"))));
	}
	
	@Test public void
	testsCssClassSubstrings(){
		WebElement element = t.findThe(tableElement().that(hasCssClass("classAAA")));
		assertThat(element, hasCssClass("classAAA"));
		assertThat(element, not(hasCssClass("classA")));
	}

	@Test public void 
    testsSwappedMatcherStillUsed(){
        t.assertThe(presenceOf(exactly(1),tableElement().that(hasCssClass("classA"))));
    }
	
	
	@Test public void
	fetchesSingleAttributeUsingTransformingFinder(){
	 	String id =  t.find(attribute("id").within(only(span().that(hasCssClass("classC")))));
	    assertThat(id, equalTo("123"));
	}
 	
	@Test public void
	fetchesMultipleAttributeUsingTransformingFinder(){
	    Collection<String> id = t.find(attribute("id").withinEach(span().that(hasCssClass("classD"))));
	    assertThat(id, contains("s1","s2","s3","s4"));
	}
	
	@Test public void
	complainsAttributeMissingUsingTransformingFinder(){
		thrown.expectMessage(allOf(containsIgnoringWhitespace("Expected: a attribute 'idx', within a unique span that has css class \"classC\""), ContainsIgnoringWhitespace.containsIgnoringWhitespace("no attribute 'idx'")));
		t.find(attribute("idx").within(only(span().that(hasCssClass("classC")))));
	}
	
	@Test public void
	complainsAttributeMissingUsingAttributesFinder(){
	    expectError("Expected: a attribute 'idx' within each span that has css class \"classD\"");
	    expectError("Not found - \n" +
	    									"within: {<span>  (class:classD) (id:s1) span1Text}\n" +
	    									"\tno attribute 'idx'");
	    t.find(attribute("idx").withinEach(span().that(hasCssClass("classD"))));
	}
	
	@Test  public void
	clicksOnEachItem(){
		expectError("Expected: a span that has text \"wibble\" to click on each one found");
		t.clickOn(each(span().that(hasText("wibble"))));
	}
	
	@Test
	public void shouldUseOptimizationOnlyForFailFastDirect() throws Exception {
		assertUsesOptimization(optimizeOnlyFinder());
	}

	@Test
	public void shouldUseOptimizationOnlyForFailFastOrdinal() throws Exception {
	assertUsesOptimization(second(optimizeOnlyFinder()));	
		
	}
	@Test
	public void shouldUseOptimizationOnlyForFailFastOnly() throws Exception {
		assertUsesOptimization(only(optimizeOnlyFinder()));	
	}
	@Test
	public void shouldUseOptimizationOnlyForFailFastWithinOther() throws Exception {
		assertUsesOptimization(optimizeOnlyFinder().withinA(span().that(hasCssClass("classC"))));
	}
	@Test
	public void shouldUseOptimizationOnlyForFailFastOtherWithinThis() throws Exception {
		assertUsesOptimization((span().that(hasCssClass("classC")).withinA(optimizeOnlyFinder())));
	}
	
	@Test
	public void shouldUseOptimizationOnlyForFailFastWhenModifiedWithThat() throws Exception {
		assertUsesOptimization(optimizeOnlyFinder().that(hasCssClass("classC")));
	}
	
	@Test
	@Ignore("unsure how to test automatedly")
	public void waitForOptimizeOnly() throws Exception {
		t.waitFor(optimizeOnlyFinder());
	}
	
	
	private <Q extends Quantity<WebElement>> void assertUsesOptimization(Finder<WebElement,Q,SearchContext> optimizeOnlyFinder){
		ExpectationChecker<SearchContext> checker = checkerForPage();
		checker.resultOfCheckingFailFast(presenceOf(optimizeOnlyFinder));
	}
	
	private ExpectationChecker<SearchContext> checkerForPage() {
		return ExpectationChecker.checkerFor((SearchContext)t.getDriver());
	}
	
	private static OptimizeOnlyAllowedFinder optimizeOnlyFinder() {
		//initialized without optimization - it should get optimized by callers
		return new OptimizeOnlyAllowedFinder(false, div().that(hasId("XXX")));
	}
	
	private static class OptimizeOnlyAllowedFinder extends BaseMFinder<WebElement, SearchContext>{

		private final boolean optimized;
		private final MFinder<WebElement, SearchContext> innerFinder;
		
		public OptimizeOnlyAllowedFinder(boolean optimized,
				MFinder<WebElement, SearchContext> innerFinder) {
			this.optimized = optimized;
			this.innerFinder = innerFinder;
		}

		@Override
		public MFinder<WebElement, SearchContext> asOptimizedFinder() {
			return new OptimizeOnlyAllowedFinder(true, innerFinder);
		}

		@Override
		public void describeTo(Description description) {
			
			description.appendText("Optimize only: " + optimized + ": ").appendDescriptionOf(innerFinder);
		}

		@Override
		public CollectionOf<WebElement> findFrom(SearchContext context,
				Description notFoundDescription) {
			if(!optimized)
				throw new AssertionError("Called without optimization");
			return innerFinder.findFrom(context, notFoundDescription);
		}
	}
	
	
	
}
