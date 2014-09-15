package jhc.redsniff.webdriver;

import static jhc.redsniff.core.FindingExpectations.presenceOf;
import static jhc.redsniff.internal.finders.AsActionableFinder.each;
import static jhc.redsniff.webdriver.Finders.attribute;
import static jhc.redsniff.webdriver.Finders.button;
import static jhc.redsniff.webdriver.Finders.cell;
import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.Finders.dropDown;
import static jhc.redsniff.webdriver.Finders.dropDownOption;
import static jhc.redsniff.webdriver.Finders.first;
import static jhc.redsniff.webdriver.Finders.form;
import static jhc.redsniff.webdriver.Finders.only;
import static jhc.redsniff.webdriver.Finders.row;
import static jhc.redsniff.webdriver.Finders.second;
import static jhc.redsniff.webdriver.Finders.span;
import static jhc.redsniff.webdriver.Finders.table;
import static jhc.redsniff.webdriver.Finders.tableElement;
import static jhc.redsniff.webdriver.Finders.third;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasCssClass;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasId;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;
import static org.openqa.selenium.lift.match.NumericalMatchers.atLeast;
import static org.openqa.selenium.lift.match.NumericalMatchers.exactly;

import java.util.Collection;

import jhc.redsniff.RedsniffTestBase;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.matchers.ContainsIgnoringWhitespace;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

@SuppressWarnings("unchecked")
public class WebTesterWithinTest extends RedsniffTestBase {


    @Test public void withinEachFormsecondDropDown(){
        Collection<WebElement> secondDropDownInEachForm = t.inEach(form())
                                                                .find(second(dropDown()));
        assertThat(secondDropDownInEachForm, contains(hasName("drop-down_1_2"), hasName("drop-down_2_2")));
    }
    
    @Test public void secondDropDownWithinEachForm(){
        Collection<WebElement> secondDropDownInEachForm = t.find(second(dropDown()).withinEach(form()));
        assertThat(secondDropDownInEachForm, contains(hasName("drop-down_1_2"), hasName("drop-down_2_2")));
    }
    
    @Test public void noAssertionUntilAction(){
    	t.inThe(form().that(hasName("nonexistent"))).inThe(div().that(hasId("nope")));
    }
    
	@Test
	public void secondDivWithinEachFormNotFound() {
		expectError("Expected: a 2nd div within each form\n"
				+ "but:\n"
				+ "Not found -\n"
				+ " within: {<form> (class:form) (name:form_1) (id:frmId) OPTION 1 OPTION 2 OPTION 3 OPTION 1 OPTION 2 OPTION 3 OPTION 1 OPTION 2 OPTION 3 Some span text Some other span text}\n"
				+ "Not found - No div found at all");
		
		Collection<WebElement> secondDropDownInEachForm = t.find(second(div())
				.withinEach(form()));
	}
	
	
	@Test 
	public void
	reasonLookingForClickIsGivenWhenSearchingOuterElementFails(){
		expectError("Expected: a 1st form that has id \"nonexistent\" to search within to find a 2nd drop-down to search within to find a 1st drop-down option to click on");
		 t.inThe(first(form().that(hasId("nonexistent"))))
			.inThe(second(dropDown()))
				.clickOn(first(dropDownOption()));
	}
	
	@Test 
	public void
	reasonLookingForClickIsGivenWhenSearchingIntermediateElementFails(){
		expectError("Expected:\n" +
					"in   1st form that has id \"frmId\":\n" +
					"a 2nd drop-down that has id \"nonexistent\" to search within to find a 1st drop-down option to click on");
		t.inThe(first(form().that(hasId("frmId"))))
			.inThe(second(dropDown().that(hasId("nonexistent"))))
				.clickOn(first(dropDownOption()));
	}
	
	@Test 
	public void
	reasonLookingForClickIsGivenWhenSearchingInnermostElementFails(){
		expectError("Expected:\n" +
				"in   1st form that has id \"frmId\"\n" +
				",in   2nd drop-down, "+
				"a 1st drop-down option that has id \"nonexistent\" to click on");
	t.inThe(first(form().that(hasId("frmId"))))
		.inThe(second(dropDown()))
			.clickOn(first(dropDownOption().that(hasId("nonexistent"))));
	}
	
	@Test
	public void transforming() throws Exception {
		assertThat((Collection<String>) t.inThe(second(form())).find(attribute("name").withinEach(dropDown())), 
				contains("drop-down_2_1","drop-down_2_2"));
		
	}
	
	@Test
	public void secondDropDownWithinADivNotFound() {
		expectError("a drop-down, within a div\n"
				+ "but:\n"
				+ "Not found - div\n"
				+ "within which to search for {drop-down}\n" 
				+ "because\n" 
				+ "No div found at all");
		Collection<WebElement> secondDropDownWithinASpan = 
				t.find(dropDown().withinA(div()));
	}
    
    @Test public void thirdDropDownWithinEachFormNotFound(){
		expectError("Expected: a 3rd drop-down within each form\n"	+
    			"but:\n" +
				"Not found -\n" +
				" within: {<form> (class:form) (name:form_2) (id:frmId) OPTION 1 OPTION 2 OPTION 3 OPTION 1 OPTION 2 OPTION 3 Some span text for 2nd form}\n" +
				"only found <2> such elements\n" +
				"[ <select> (name:drop-down_2_1) OPTION 1 OPTION 2 OPTION 3 ], [ <select> (name:drop-down_2_2) OPTION 1 OPTION 2 OPTION 3 ]");
    	 Collection<WebElement> thirdDropDownInEachForm =   t.find(third(dropDown()).withinEach(form()));
    }
    
    @Test public void firstDropDownThatsWithinAForm(){
        WebElement firstDropDown = t.find(first(dropDown()));
        assertThat(firstDropDown, hasName("drop-down_0"));
        
        WebElement firstDropDownInAForm = t.find(first(dropDown().withinA(form())));
        assertThat(firstDropDownInAForm, hasName("drop-down_1_1"));
    }
        
     @Test public void secondDropDownInSecondForm(){
         
        WebElement secondDropDownInSecondForm = t.find(second(dropDown()).within(second(form())));
        assertThat(secondDropDownInSecondForm, hasName("drop-down_2_2"));
     }
     
     @Test public void thirdOptionInFirstDropDownInSecondForm(){
         
        WebElement thirdOptionInFirstDropDownInSecondForm 
        = t.find(third(dropDownOption()).within(first(dropDown()).within(second(form()))));
        
        assertThat(thirdOptionInFirstDropDownInSecondForm, hasId("option_2_1_3"));
     }
      
     @Test public void atLeast2OptionsInEachDropDownInEachForm(){
    	 expectError("Expected: \n"
				+ ", in each form\n"
				+ ", in each drop-down, there to be a value greater than <3> of drop-down option\n"
				+ "but:\n"
				+ "within {<select> (name:drop-down_1_1) OPTION 1 OPTION 2 OPTION 3\n"
				+ "}\n" + ":found <3> such elements");
    	 t.inEach(form())
             .inEach(dropDown())
                 .assertThe(presenceOf(atLeast(4), dropDownOption()));
     }
     
     @Test public void clickOnSecondDropDownOption(){
         t.inEach(form())
             .clickOn(each(dropDownOption()));
     }
     
     @Test public void inEachFormAbsence(){
         t.inEach(form())
             .assertAbsenceOf(dropDown().that(hasName("brian")));
     }
     
     
     @Test public void forTheSingleForm2Levels(){
    	 t.inThe(second(form()))
    	 	.inThe(first(dropDown()))
    	 		.assertPresenceOf(dropDownOption().that(hasId("option_2_1_2")));
     }
     
     @Test public void forTheSingleFormEachDropDown(){
     	 t.inThe(second(form()))
     	 	.inEach(dropDown())
     	 		.assertPresenceOf(dropDownOption().that(hasId(startsWith("option_2"))));
      }
     
     @Test
     @Ignore
     public void inEachFormSecondDropDown(){
     	 t.inEach(form())
     	 	.inThe(second(dropDown()))
     	 		.assertPresenceOf(dropDownOption().that(hasId(endsWith("_2_2"))));
      }
     
     
     
     @Test public void multipleWithinSingleWithinSingle(){
         MFinder<WebElement, SearchContext> dropDownOptionsInFirstDropDownInFirstForm = 
                 dropDownOption()
                 .withinThe(first(dropDown()))
                 .withinThe(first(form()));
         
         Collection<WebElement> options = t.find(dropDownOptionsInFirstDropDownInFirstForm);
         assertThat(options,  contains(hasId("option_1_1_1"), hasId("option_1_1_2"), hasId("option_1_1_3")));
     }
     
     @Test public void multipleWithinSingleWithinSingleNotFound(){
    	 
    	 
    	 expectError("Expected: a span, within the 1st drop-down, within a 1st form\n" +
    	 		"but:\n" +
    	 		"Not found - \n" +
    	 		"within: <select> (name:drop-down_1_1) OPTION 1 OPTION 2 OPTION 3\n" +
    	 		"No span found at all");
         MFinder<WebElement, SearchContext> spanInFirstDropDownInFirstForm = 
                 span()
                 .withinThe(first(dropDown()))
                 .withinThe(first(form()));
         
         t.assertPresenceOf(spanInFirstDropDownInFirstForm);
     }
     
     @Test public void multipleWithinSingleWithinNonUniqueSingle(){
         thrown.expectMessage(allOf(
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("Expected: a drop-down option, "),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("within the 1st drop-down,"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("within a unique form"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("Was not unique: form")));
         
         MFinder<WebElement, SearchContext> dropDownOptionsInFirstDropDownInOnlyForm = 
                 dropDownOption()
                 .withinThe(first(dropDown()))
                 .withinThe(only(form()));
         
        t.find(dropDownOptionsInFirstDropDownInOnlyForm);
     }
     
     @Test public void multipleWithinSingleWithinNonExistentSingle(){
         thrown.expectMessage(allOf(
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("Expected: a drop-down option, "),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("within the 1st drop-down,"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("within a unique form"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("Was not unique: form")));
         
         MFinder<WebElement, SearchContext> dropDownOptionsInFirstDropDownInOnlyForm = 
                 dropDownOption()
                 .withinThe(first(dropDown()))
                 .withinThe(only(form()));
         
        t.find(dropDownOptionsInFirstDropDownInOnlyForm);
     }
     
     @Test public void singleWithinSingleWithinSingle(){
         SFinder<WebElement, SearchContext> dropDownOptionsInFirstDropDownInFirstForm = 
                 third(dropDownOption())
                 .within(second(dropDown()))
                 .within(first(form()));
         
         WebElement option = t.find(dropDownOptionsInFirstDropDownInFirstForm);
         assertThat(option,  hasId("option_1_2_3"));
     }
     
     @Test public void singleWithinNonExistentSingleWithinSingle(){
         
         thrown.expectMessage(allOf(
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("Expected: a 3rd drop-down option within:"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("the unique input that has type (\"button\" or \"submit\")"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("within a 1st form"),
                 ContainsIgnoringWhitespace.containsIgnoringWhitespace("Did not find any input that has type (\"button\" or \"submit\")")));
         
         SFinder<WebElement, SearchContext> dropDownOptionsInFirstDropDownInFirstForm = 
                 third(dropDownOption())
                 .within(only(button()))
                 .within(first(form()));
         
         t.find(dropDownOptionsInFirstDropDownInFirstForm);
     }
     
     @Test public void inEachFormFindThirdOptionInEachDropDown(){
         Collection<WebElement> thirdOptionInEachDropDown = 
        		 t.inEach(form())
        		 	.find(third(dropDownOption()).withinEach(dropDown()));
         assertThat(thirdOptionInEachDropDown, contains(hasId("option_1_1_3"), hasId("option_1_2_3"), hasId("option_1_3_3"), hasId("option_2_1_3"), hasId("option_2_2_3")));
     }
     
     @Test 
     public void testingDifferentContextTypes(){
    	 t.inEach(table(tableElement().that(hasCssClass("no"))))
    	     .inEach(row())
    	         .assertThe(presenceOf(exactly(3), cell()));
     }
}
