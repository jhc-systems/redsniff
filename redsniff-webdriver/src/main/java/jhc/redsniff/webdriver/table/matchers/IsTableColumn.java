package jhc.redsniff.webdriver.table.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.internal.matchers.TrimmedStringMatcher.trimmedIs;
import static org.hamcrest.Matchers.equalTo;
import jhc.redsniff.html.tables.TableColumn;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.beans.HasPropertyWithValue;
//Not sure we should be extending HasPropertyWithValue - composition might be better
public  class IsTableColumn extends CheckAndDiagnoseTogetherMatcher<TableColumn>{
	
	
	private static enum ColumnIdentifierType {COLUMN_HEADER("columnHeader"), COLUMN_DISPLAY_INDEX("displayIndex");
	 	public final String propertyName;
	 	ColumnIdentifierType(String propertyName){this.propertyName = propertyName;}
	}
	
	private final Matcher<?> identifierMatcher;
	private final ColumnIdentifierType identifierType;
	private final Matcher<TableColumn> columnMatcher;
	private final String identifierDescription;
	
	public IsTableColumn(ColumnIdentifierType identifierType,Matcher<?> identifierMatcher) {
		this(identifierType,identifierMatcher,null);
	}
	
	
	public IsTableColumn(ColumnIdentifierType identifierType,Matcher<?> identifierMatcher, String identifierDescription) {
		this.identifierMatcher = identifierMatcher;
		this.identifierType = identifierType;
		this.columnMatcher = new HasPropertyWithValue<TableColumn>(identifierType.propertyName, identifierMatcher);
		this.identifierDescription = identifierDescription;
	}


	public void describeTo(Description description) {
		if(identifierDescription!=null)
			description.appendText("<").appendText(identifierDescription).appendText(">");
		else {
			switch(identifierType){
			case COLUMN_HEADER:
				description.appendText(" with header ");
				description.appendDescriptionOf(identifierMatcher);
				break;
			case COLUMN_DISPLAY_INDEX:
				description.appendText("[").appendDescriptionOf(identifierMatcher).appendText("]");
				break;
			default:
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Factory
	public static Matcher<TableColumn> isColumnIdentifiedBy(Object identifier){
		if(identifier instanceof String){
			String idString = (String) identifier;
			return new IsTableColumn(ColumnIdentifierType.COLUMN_HEADER, trimmedIs(idString), idString);
		}
		else if(identifier instanceof Integer)
			return new IsTableColumn(ColumnIdentifierType.COLUMN_DISPLAY_INDEX,equalTo((Integer)identifier));
		else if(identifier instanceof Matcher)
			return new IsTableColumn(ColumnIdentifierType.COLUMN_HEADER,(Matcher<String>)identifier);
		else 
			throw new AssertionError("cannot identifify column with "+ identifier + " please supply an integer column index or a String column header");
	}


	@Override
	protected boolean matchesSafely(TableColumn item,
			Description mismatchDescription) {
	    return matchAndDiagnose(columnMatcher, item, mismatchDescription);
	}
}




